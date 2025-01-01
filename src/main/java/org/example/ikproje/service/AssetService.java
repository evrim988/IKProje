package org.example.ikproje.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.NewAssetRequestDto;
import org.example.ikproje.entity.Asset;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EAssetStatus;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.AssetRepository;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwAsset;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final JwtManager jwtManager;
    private final UserService userService;
    private final EmailService emailService;


    //personelin üzerine atanan tüm zimmetleri görmesi için (PENDING ve ACCEPTED) rejected olanların statei passive yapıyorum.
    public List<VwAsset> getAllPersonelAssets(String token){
        Optional<Long> userIdOptional = jwtManager.validateToken(token);
        if(userIdOptional.isPresent()){
            return assetRepository.getAllVwAssetsByUserId(userIdOptional.get());
        }
        throw new IKProjeException(ErrorType.INVALID_TOKEN);
    }

    //Şirket yöneticisi için personelin üzerine atanan zimmetlerin listesi
    public List<VwAsset> getAssetListOfPersonel(String token,Long personelId){
        User companyManager = userService.getUserByToken(token);
        if(companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)){
            User user = userService.findById(personelId).orElseThrow(()->new IKProjeException(ErrorType.USER_NOTFOUND));
            //Aynı şirkette mi çalışıyorlar ?
            if(user.getCompanyId().equals(companyManager.getCompanyId())){
                return assetRepository.getAllVwAssetsByUserId(user.getId());
            }
        }
        throw new IKProjeException(ErrorType.INVALID_TOKEN);
    }

    //companyManagerin personele yeni zimmet ataması için
    public Boolean assignNewAssetToPersonel(NewAssetRequestDto dto){
        User personelManager = userService.getUserByToken(dto.token());
        User personelToAssign = userService.findById(dto.personalId()).orElseThrow(()->new IKProjeException(ErrorType.USER_NOTFOUND));
        if(!personelManager.getCompanyId().equals(personelToAssign.getCompanyId())) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        assetRepository.save(Asset.builder()
                .personelId(personelToAssign.getId())
                .companyManagerId(personelManager.getId())
                .description(dto.description())
                .assetType(dto.assetType())
                .build()) ;
        return true;
    }

    //personelin üzerine atanan zimmeti onaylaması
    public Boolean approveAssetAssignment(String token, Long assetId){
        Long personelId = jwtManager.validateToken(token).orElseThrow(()->new IKProjeException(ErrorType.INVALID_TOKEN));
        Asset asset = assetRepository.findById(assetId).orElseThrow(()->new IKProjeException(ErrorType.ASSET_NOT_FOUND));
        if(!personelId.equals(asset.getPersonelId()) || !asset.getStatus().equals(EAssetStatus.PENDING)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        asset.setStatus(EAssetStatus.CONFIRMED);
        asset.setGivenDate(LocalDate.now());
        assetRepository.save(asset);
        return true;
    }

    //personelin üzerine atanan zimmeti reddetmesi, reddetince EState Passive yapıyorum, belki sadece rejected bırakılabilir
    @Transactional
    public Boolean rejectAssetAssignment(String token, Long assetId,String rejectMessage){
        Long personelId = jwtManager.validateToken(token).orElseThrow(()->new IKProjeException(ErrorType.INVALID_TOKEN));
        Asset asset = assetRepository.findById(assetId).orElseThrow(()->new IKProjeException(ErrorType.ASSET_NOT_FOUND));
        if(!personelId.equals(asset.getPersonelId()) || !asset.getStatus().equals(EAssetStatus.PENDING)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        asset.setStatus(EAssetStatus.REJECTED);
        asset.setState(EState.PASSIVE);
        String companyManagerMail = userService.findEmailByUserId(asset.getCompanyManagerId());
        String personelMail = userService.findEmailByUserId(asset.getPersonelId());
        emailService.sendAssetAssignmentRejectionMessage(companyManagerMail, personelMail, rejectMessage);
        assetRepository.save(asset);
        return true;
    }






}
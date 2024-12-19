package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.AssetRepository;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwAsset;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final JwtManager jwtManager;


    public List<VwAsset> getAllPersonetAssets(String token){
        Optional<Long> userIdOptional = jwtManager.validateToken(token);
        if(userIdOptional.isPresent()){
            return assetRepository.getAllVwAssetsByUserId(userIdOptional.get());
        }
        throw new IKProjeException(ErrorType.INVALID_TOKEN);
    }
}
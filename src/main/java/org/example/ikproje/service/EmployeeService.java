package org.example.ikproje.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.UpdatePersonelProfileRequestDto;
import org.example.ikproje.entity.Address;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserDetails;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.UserRepository;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwPersonel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final UserRepository userRepository;
    private final JwtManager jwtManager;
    private final UserDetailsService userDetailsService;
    private final AddressService addressService;


    public VwPersonel getPersonelProfile(String token){
        Optional<Long> userIdOpt = jwtManager.validateToken(token);
        if (userIdOpt.isEmpty()) throw new IKProjeException(ErrorType.INVALID_TOKEN);
        Optional<VwPersonel> vwPersonelOptional = userRepository.findVwPersonelByUserId(userIdOpt.get());
        if (vwPersonelOptional.isEmpty()) throw new IKProjeException(ErrorType.USER_NOTFOUND);

        return vwPersonelOptional.get();
    }


    @Transactional
    public Boolean updatePersonelProfile(UpdatePersonelProfileRequestDto dto){
        User personel = getUserByToken(dto.token());
        if(!dto.id().equals(personel.getId())) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        personel.setFirstName(dto.firstName());
        personel.setLastName(dto.lastName());
        personel.setEmail(dto.email());
        personel.setPhone(dto.phone());
        personel.setAvatarUrl(dto.avatarUrl());
        personel.setUpdateAt(System.currentTimeMillis());
        userRepository.save(personel);

        UserDetails personelDetails = userDetailsService.findByUserId(personel.getId());
        personelDetails.setHireDate(dto.hireDate());
        personelDetails.setTcNo(dto.tcNo());
        personelDetails.setSgkNo(dto.sgkNo());
        personelDetails.setBirthDate(dto.birthDate());
        personelDetails.setDepartmentType(dto.departmentType());
        personelDetails.setUpdateAt(System.currentTimeMillis());
        userDetailsService.save(personelDetails);

        Address personelAddress = addressService.findById(personelDetails.getAddressId());
        personelAddress.setRegion(dto.region());
        personelAddress.setCity(dto.city());
        personelAddress.setDistrict(dto.district());
        personelAddress.setNeighbourhood(dto.neighbourhood());
        personelAddress.setStreet(dto.street());
        personelAddress.setPostalCode(dto.postalCode());
        personelAddress.setAptNumber(dto.aptNumber());
        personelAddress.setUpdateAt(System.currentTimeMillis());
        addressService.save(personelAddress);
        return true;
    }



    private User getUserByToken(String token){
        Optional<Long> userIdOpt = jwtManager.validateToken(token);
        if (userIdOpt.isEmpty()) throw new IKProjeException(ErrorType.INVALID_TOKEN);
        Optional<User> optUser = userRepository.findById(userIdOpt.get());
        if (optUser.isEmpty()) throw new IKProjeException(ErrorType.USER_NOTFOUND);
        return optUser.get();
    }
}

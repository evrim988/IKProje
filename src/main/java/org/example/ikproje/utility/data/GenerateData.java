package org.example.ikproje.utility.data;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Admin;
import org.example.ikproje.repository.*;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GenerateData {

    private final AdminRepository adminRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CompanyRepository companyRepository;
    private final UserDetailsRepository userDetailsRepository;


    @PostConstruct
    public void generateData(){
        if(adminRepository.count() == 0){
            Admin admin = Admin.builder()
                    .email("admin@test.com")
                    .password("java15")
                    .build();
            adminRepository.save(admin);
        }
//        if(assetRepository.count() == 0){
//            assetRepository.saveAll(GenerateAsset.generateAsset());
//        }

        if(userRepository.count() == 0){
            userRepository.save(GenerateUser.generateUser());
            userRepository.saveAll(GenerateUser.generateEmployee());
        }
        if(companyRepository.count() == 0){
            companyRepository.save(GenerateUser.generateCompany());
        }
        if(addressRepository.count() == 0){
            addressRepository.saveAll(GenerateUser.generateAddress());
        }
        if(userDetailsRepository.count() == 0){
            userDetailsRepository.saveAll(GenerateUser.generateUserDetails());
        }

    }
}

package org.example.ikproje.utility.data;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Admin;
import org.example.ikproje.repository.AdminRepository;
import org.example.ikproje.repository.AssetRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateData {

    private final AdminRepository adminRepository;
    private final AssetRepository assetRepository;


    @PostConstruct
    public void generateData(){
        if(adminRepository.count() == 0){
            Admin admin = Admin.builder()
                    .email("admin@test.com")
                    .password("java15")
                    .build();
            adminRepository.save(admin);
        }
        if(assetRepository.count() == 0){
            assetRepository.saveAll(GenerateAsset.generateAsset());
        }

    }
}

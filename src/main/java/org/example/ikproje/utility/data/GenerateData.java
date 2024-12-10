package org.example.ikproje.utility.data;


import jakarta.annotation.PostConstruct;
import org.example.ikproje.entity.Admin;
import org.example.ikproje.repository.AdminRepository;
import org.springframework.stereotype.Component;

@Component
public class GenerateData {

    private final AdminRepository adminRepository;

    public GenerateData(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostConstruct
    public void generateAdmin(){
        if(adminRepository.count() == 0){
            Admin admin = Admin.builder()
                    .email("admin@test.com")
                    .password("java15")
                    .build();
            adminRepository.save(admin);
        }

    }
}

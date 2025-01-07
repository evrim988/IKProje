package org.example.ikproje.utility.data;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Admin;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.User;
import org.example.ikproje.repository.*;
import org.example.ikproje.service.CloudinaryService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class GenerateData {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CompanyRepository companyRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final CloudinaryService cloudinaryService;


    @PostConstruct
    public void generateData() throws IOException {
        if(adminRepository.count() == 0){
            Admin admin = Admin.builder()
                    .id(99999L)
                    .email("admin@test.com")
                    .password("java15")
                    .build();
            adminRepository.save(admin);
        }

        if(userRepository.count() == 0){
            List<User> managers = GenerateUser.generateCompanyManager();
            userRepository.saveAll(managers);


            userRepository.saveAll(GenerateUser.generateEmployee());
        }
        if(companyRepository.count() == 0){
            List<Company> companyList = GenerateUser.generateCompany();
            File file1 = new File("D:/Projects/IKProje/src/main/java/org/example/ikproje/utility/images/koc.jpg");
            File file2 = new File("D:/Projects/IKProje/src/main/java/org/example/ikproje/utility/images/trump.jpg");
            File file3 = new File("D:/Projects/IKProje/src/main/java/org/example/ikproje/utility/images/apple.png");
            File file4 = new File("D:/Projects/IKProje/src/main/java/org/example/ikproje/utility/images/microsoft.png");
            File file5 = new File("D:/Projects/IKProje/src/main/java/org/example/ikproje/utility/images/amazon.png");
            companyList.get(0).setLogo(cloudinaryService.uploadNormalFile(file1));
            companyList.get(1).setLogo(cloudinaryService.uploadNormalFile(file2));
            companyList.get(2).setLogo(cloudinaryService.uploadNormalFile(file3));
            companyList.get(3).setLogo(cloudinaryService.uploadNormalFile(file4));
            companyList.get(4).setLogo(cloudinaryService.uploadNormalFile(file5));
            companyRepository.saveAll(companyList);


        }
        if(addressRepository.count() == 0){
            addressRepository.saveAll(GenerateUser.generateAddress());
        }
        if(userDetailsRepository.count() == 0){
            userDetailsRepository.saveAll(GenerateUser.generateUserDetails());
        }

    }
}

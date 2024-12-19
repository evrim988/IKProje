package org.example.ikproje.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.ikproje.entity.Address;
import org.example.ikproje.entity.Asset;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EUserDepartmentType;
import org.example.ikproje.entity.enums.EUserRole;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

//Personel ve şirket yöneticileri personel sayfasına gittiği zaman bu bilgileri görebilsin
public class VwPersonel {



    Long id;
    String firstName;
    String lastName;
    String email;
    String companyName;
    String phone;
    String avatarUrl;
    EUserRole userRole;
    //adres
    String region;
    String city;
    String district;
    String neighbourhood;
    String street;
    String postalCode;
    String aptNumber;
    //userDetails
    LocalDate hireDate;
    String tcNo;
    String sgkNo;
    LocalDate birthDate;
    EUserDepartmentType departmentType;
}

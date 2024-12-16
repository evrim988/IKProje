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
    List<VwAsset> personelAssets; //personele atanmış zimmetler

    //izinler sayfası, harcamalar sayfası için listeler eklenecek
    //List'ler Eager Fetch ile getirilebilir ?


    public VwPersonel(Long id, String firstName, String lastName, String email, String companyName, String phone, String avatarUrl, EUserRole userRole, String region, String city, String district, String neighbourhood, String street, String postalCode, String aptNumber, LocalDate hireDate, String tcNo, String sgkNo, LocalDate birthDate, EUserDepartmentType departmentType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.userRole = userRole;
        this.region = region;
        this.city = city;
        this.district = district;
        this.neighbourhood = neighbourhood;
        this.street = street;
        this.postalCode = postalCode;
        this.aptNumber = aptNumber;
        this.hireDate = hireDate;
        this.tcNo = tcNo;
        this.sgkNo = sgkNo;
        this.birthDate = birthDate;
        this.departmentType = departmentType;
        this.personelAssets = new ArrayList<>();
    }

}

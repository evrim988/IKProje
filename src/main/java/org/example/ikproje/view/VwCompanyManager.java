package org.example.ikproje.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ikproje.entity.enums.EUserDepartmentType;
import org.example.ikproje.entity.enums.EUserRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VwCompanyManager {

    public VwCompanyManager(Long id, Long companyId,String firstName, String lastName, String email, String companyName, String phone, String avatarUrl, EUserRole userRole, String region, String city, String district, String neighbourhood, String street, String postalCode, String aptNumber, LocalDate hireDate, String tcNo, String sgkNo, LocalDate birthDate) {
        this.id = id;
        this.companyId = companyId;
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
        this.personelList = new ArrayList<>();
    }

    //user
    Long id;
    Long companyId;
    String firstName;
    String lastName;
    String email;
    String companyName;
    String phone;
    String avatarUrl;
    EUserRole userRole;
    //userDetails
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
    List<VwPersonel> personelList;

    //başka şeyler de eklenebilir



}

package org.example.ikproje.utility.data;

import org.example.ikproje.entity.Address;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserDetails;
import org.example.ikproje.entity.enums.EIsApproved;
import org.example.ikproje.entity.enums.EUserDepartmentType;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.utility.EncryptionManager;


import java.time.LocalDate;
import java.util.List;

public class GenerateUser {

    public static User generateUser(){
        return   User.builder()
                .userRole(EUserRole.COMPANY_MANAGER)
                .phone("0544 222 66 77")
                .companyId(1L)
                .firstName("Vehbi")
                .lastName("Koç")
                .isApproved(EIsApproved.APPROVED)
                .isMailVerified(true)
                .email("vehbi@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();
    }

    public static List<User> generateEmployee(){
        User personel1 =  User.builder()
                .userRole(EUserRole.EMPLOYEE)
                .phone("0555 444 33 22")
                .companyId(1L)
                .firstName("Gizem")
                .lastName("Oray")
                .isMailVerified(true)
                .email("gizem@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();

        User personel2 =  User.builder()
                .userRole(EUserRole.EMPLOYEE)
                .phone("0555 555 22 11")
                .companyId(1L)
                .firstName("Mehmet")
                .lastName("Karaca")
                .isMailVerified(true)
                .email("mehmet@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();

        User personel3 =  User.builder()
                .userRole(EUserRole.EMPLOYEE)
                .phone("0444 333 11 44")
                .companyId(1L)
                .firstName("Furkan")
                .lastName("Gök")
                .isMailVerified(true)
                .email("furkan@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();
        return List.of(personel1, personel2, personel3);
    }

    public static List<UserDetails> generateUserDetails(){
        UserDetails userDetails1 = UserDetails.builder()
                .tcNo("26145691856")
                .sgkNo("154624")
                .hireDate(LocalDate.of(2024,4,9))
                .userId(1L)
                .salary(45000.0)
                .departmentType(EUserDepartmentType.HR)
                .birthDate(LocalDate.of(1996,5,15))
                .addressId(1L)
                .build();

        UserDetails userDetails2 = UserDetails.builder()
                .tcNo("84576634354")
                .sgkNo("248799")
                .hireDate(LocalDate.of(2023,11,25))
                .userId(2L)
                .salary(65000.0)
                .departmentType(EUserDepartmentType.SECURITY)
                .birthDate(LocalDate.of(1996,2,4))
                .addressId(2L)
                .build();
        UserDetails userDetails3 = UserDetails.builder()
                .tcNo("44758564567")
                .sgkNo("165234")
                .hireDate(LocalDate.of(2022,8,19))
                .userId(3L)
                .salary(105000.0)
                .departmentType(EUserDepartmentType.IT)
                .birthDate(LocalDate.of(1994,5,12))
                .addressId(3L)
                .build();

        UserDetails userDetails4 = UserDetails.builder()
                .tcNo("44724454567")
                .sgkNo("986578")
                .hireDate(LocalDate.of(2023,4,21))
                .userId(4L)
                .salary(65000.0)
                .departmentType(EUserDepartmentType.ADMINISTRATION)
                .birthDate(LocalDate.of(1994,5,12))
                .addressId(4L)
                .build();
        return List.of(userDetails1, userDetails2, userDetails3, userDetails4);
    }

    public static Company generateCompany(){
        return Company.builder()
                .name("KOC HOLDING")
                .addressId(5L)
                .phone("05362065163")
                .foundationDate(LocalDate.of(1981,4,12))
                .build();
    }

    public static List<Address> generateAddress(){
        Address address1 = Address.builder().city("İSTANBUL").build();
        Address address2 = Address.builder().city("MERSİN").build();
        Address address3 = Address.builder().city("İZMİR").build();
        Address address4 = Address.builder().city("KOCAELİ").build();
        Address address5 = Address.builder().city("BURSA").build();
        return List.of(address1, address2, address3, address4,address5);
    }




}

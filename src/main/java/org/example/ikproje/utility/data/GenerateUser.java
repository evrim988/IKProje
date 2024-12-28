package org.example.ikproje.utility.data;

import org.example.ikproje.entity.Address;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserDetails;
import org.example.ikproje.entity.enums.EGender;
import org.example.ikproje.entity.enums.EIsApproved;
import org.example.ikproje.entity.enums.EUserDepartmentType;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.utility.EncryptionManager;


import java.time.LocalDate;
import java.util.List;

public class GenerateUser {

    public static List<User> generateCompanyManager(){
        User companyManager1 = User.builder()
                .userRole(EUserRole.COMPANY_MANAGER)
                .phone("0544 222 66 77")
                .companyId(1L)
                .firstName("Vehbi")
                .lastName("Koç")
                .gender(EGender.MALE)
                .isApproved(EIsApproved.APPROVED)
                .isMailVerified(true)
                .email("vehbi@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();

        User companyManager2 = User.builder()
                .userRole(EUserRole.COMPANY_MANAGER)
                .phone("0544 222 14 22")
                .companyId(2L)
                .firstName("Donald")
                .lastName("Trump")
                .gender(EGender.MALE)
                .isApproved(EIsApproved.APPROVED)
                .isMailVerified(true)
                .email("trump@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();
        return List.of(companyManager1, companyManager2);
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
                .gender(EGender.FEMALE)
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
                .gender(EGender.MALE)
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
                .gender(EGender.MALE)
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();

        User personel4 =  User.builder()
                .userRole(EUserRole.EMPLOYEE)
                .phone("0444 333 11 44")
                .companyId(2L)
                .firstName("Michael")
                .lastName("Jackson")
                .isMailVerified(true)
                .email("michael@gmail.com")
                .gender(EGender.MALE)
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();
        User personel5 =  User.builder()
                .userRole(EUserRole.EMPLOYEE)
                .phone("0444 333 11 44")
                .companyId(2L)
                .firstName("Dave")
                .lastName("Johnson")
                .isMailVerified(true)
                .email("dave@gmail.com")
                .gender(EGender.MALE)
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();

        User personel6 =  User.builder()
                .userRole(EUserRole.EMPLOYEE)
                .phone("0444 333 11 44")
                .companyId(2L)
                .firstName("Alice")
                .lastName("White")
                .isMailVerified(true)
                .email("alice@gmail.com")
                .gender(EGender.FEMALE)
                .password(EncryptionManager.getEncryptedPassword("sifre123"))
                .build();
        return List.of(personel1, personel2, personel3, personel4, personel5, personel6);
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
        UserDetails userDetails5 = UserDetails.builder()
                .tcNo("26145691856")
                .sgkNo("154624")
                .hireDate(LocalDate.of(2024,4,9))
                .userId(5L)
                .salary(45000.0)
                .departmentType(EUserDepartmentType.HR)
                .birthDate(LocalDate.of(1996,5,15))
                .addressId(5L)
                .build();

        UserDetails userDetails6 = UserDetails.builder()
                .tcNo("84576634354")
                .sgkNo("248799")
                .hireDate(LocalDate.of(2023,11,25))
                .userId(6L)
                .salary(65000.0)
                .departmentType(EUserDepartmentType.SECURITY)
                .birthDate(LocalDate.of(1996,2,4))
                .addressId(6L)
                .build();
        UserDetails userDetails7 = UserDetails.builder()
                .tcNo("44758564567")
                .sgkNo("165234")
                .hireDate(LocalDate.of(2022,8,19))
                .userId(7L)
                .salary(105000.0)
                .departmentType(EUserDepartmentType.OPERATIONS)
                .birthDate(LocalDate.of(1994,5,12))
                .addressId(7L)
                .build();

        UserDetails userDetails8 = UserDetails.builder()
                .tcNo("44724454567")
                .sgkNo("986578")
                .hireDate(LocalDate.of(2023,4,21))
                .userId(8L)
                .salary(65000.0)
                .departmentType(EUserDepartmentType.ADMINISTRATION)
                .birthDate(LocalDate.of(1994,5,12))
                .addressId(8L)
                .build();
        return List.of(userDetails1, userDetails2, userDetails3, userDetails4,userDetails5,userDetails6,userDetails7,userDetails8);
    }

    public static List<Company> generateCompany(){
        Company kocHolding = Company.builder()
                .name("KOC HOLDING")
                .addressId(9L)
                .phone("05362065163")
                .foundationDate(LocalDate.of(1981, 4, 12))
                .build();
        Company trumpHolding = Company.builder()
                .name("TRUMP HOLDING")
                .addressId(10L)
                .phone("05224442255")
                .foundationDate(LocalDate.of(1967,5,16))
                .build();
        return List.of(kocHolding, trumpHolding);
    }

    public static List<Address> generateAddress(){
        Address address1 = Address.builder().city("İSTANBUL").build();
        Address address2 = Address.builder().city("NEW YORK").build();
        Address address3 = Address.builder().city("İZMİR").build();
        Address address4 = Address.builder().city("KOCAELİ").build();
        Address address5 = Address.builder().city("SAMSUN").build();
        Address address6 = Address.builder().city("NEW YORK").build();
        Address address7 = Address.builder().city("NEW YORK").build();
        Address address8 = Address.builder().city("NEW YORK").build();
        Address address9 = Address.builder().city("İSTANBUL").build();
        Address address10 = Address.builder().city("NEW YORK").build();
        return List.of(address1, address2, address3, address4,address5,address6,address7,address8,address9,address10);
    }




}
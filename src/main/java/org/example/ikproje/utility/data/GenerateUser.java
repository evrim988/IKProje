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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateUser {

    public static List<User> generateCompanyManager(){
        User cm1 = User.builder().userRole(EUserRole.COMPANY_MANAGER).phone("0544 222 66 77").companyId(1L).firstName("Vehbi")
                .lastName("Koç").gender(EGender.MALE).isApproved(EIsApproved.APPROVED).isMailVerified(true).email("vehbi@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123")).build();
        User cm2 = User.builder().userRole(EUserRole.COMPANY_MANAGER).phone("0544 222 14 22").companyId(2L).firstName("Donald").lastName("Trump")
                .gender(EGender.MALE).isApproved(EIsApproved.APPROVED).isMailVerified(true).email("trump@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123")).build();
        User cm3 = User.builder().userRole(EUserRole.COMPANY_MANAGER).phone("0534 325 17 55").companyId(3L).firstName("Steve").lastName("Jobs")
                .gender(EGender.MALE).isApproved(EIsApproved.APPROVED).isMailVerified(true).email("steve@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123")).build();
        User cm4 = User.builder().userRole(EUserRole.COMPANY_MANAGER).phone("0534 242 24 52").companyId(4L).firstName("Bill").lastName("Gates")
                .gender(EGender.MALE).isApproved(EIsApproved.APPROVED).isMailVerified(true).email("bill@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123")).build();
        User cm5 = User.builder().userRole(EUserRole.COMPANY_MANAGER).phone("0545 321 79 91").companyId(5L).firstName("Jeff").lastName("Bezos")
                .gender(EGender.MALE).isApproved(EIsApproved.APPROVED).isMailVerified(true).email("jeff@gmail.com")
                .password(EncryptionManager.getEncryptedPassword("sifre123")).build();
        return List.of(cm1, cm2,cm3,cm4,cm5);
    }

    public static List<User> generateEmployee() {
        User p1 = User.builder().userRole(EUserRole.EMPLOYEE).phone("0555 444 33 22").companyId(1L).firstName("Gizem").lastName("Oray")
                .isMailVerified(true).email("gizem@gmail.com").gender(EGender.FEMALE).password(EncryptionManager.getEncryptedPassword("sifre123")).build();
        User p2 = User.builder().userRole(EUserRole.EMPLOYEE).phone("0555 555 22 11").companyId(1L).firstName("Mehmet").lastName("Karaca")
                .isMailVerified(true).email("mehmet@gmail.com").gender(EGender.MALE).password(EncryptionManager.getEncryptedPassword("sifre123")).build();
        User p3 = User.builder().userRole(EUserRole.EMPLOYEE).phone("0444 333 11 44").companyId(1L).firstName("Furkan").lastName("Gök")
                .isMailVerified(true).email("furkan@gmail.com").gender(EGender.MALE).password(EncryptionManager.getEncryptedPassword("sifre123")).build();
        User p4 = User.builder().userRole(EUserRole.EMPLOYEE).phone("0555 123 45 67").companyId(1L).firstName("Merve").lastName("Tuncer")
                .isMailVerified(true).email("merve@gmail.com").gender(EGender.FEMALE).password(EncryptionManager.getEncryptedPassword("sifre123")).build();
        User p5 = User.builder().userRole(EUserRole.EMPLOYEE).phone("0555 987 65 43").companyId(1L).firstName("Burak").lastName("Demir")
                .isMailVerified(true).email("burak@gmail.com").gender(EGender.MALE).password(EncryptionManager.getEncryptedPassword("sifre123")).build();

        List<User> employees = new ArrayList<>(List.of(p1, p2, p3, p4, p5));

        long[] companyIds = {1L, 2L, 3L, 4L, 5L};
        String[][] names = {
                {"Ali", "Veli", "Can", "Ayşe", "Fatma", "Kemal", "Zeynep", "Efe", "Elif", "Hüseyin"},
                {"Michael", "Sarah", "Robert", "Anna", "James", "Emily", "John", "Jessica", "William", "Sophia"},
                {"Carlos", "Maria", "Jorge", "Luisa", "Pedro", "Ana", "Juan", "Isabella", "Diego", "Gabriela"},
                {"Hans", "Klara", "Fritz", "Sophie", "Karl", "Emma", "Max", "Lina", "Otto", "Greta"},
                {"Liam", "Emma", "Noah", "Olivia", "Mason", "Ava", "Logan", "Isabella", "Lucas", "Mia"}
        };
        List<String> lastNames = List.of(
                "Yılmaz", "Kaya", "Şahin", "Çelik", "Demir", "Koç", "Aydın", "Arslan", "Ertürk", "Güneş",
                "Johnson", "Gruber", "Jackson", "James", "McCarthy", "Holmes", "Queen", "McNulty",
                "Smith", "Taylor", "Anderson", "Brown", "Clark", "Wright", "Harris", "Martin", "Lewis", "Walker",
                "Garcia", "Rodriguez", "Martínez", "Hernández", "López", "Gonzalez", "Perez", "Sanchez",
                "Schmidt", "Müller", "Weber", "Schneider", "Fischer", "Becker", "Hoffmann", "Schwarz", "Koch", "Richter"
        );

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                employees.add(User.builder()
                        .userRole(EUserRole.EMPLOYEE)
                        .phone("05" + (i + 1) + "444" + (30 + j) + (10 + j))
                        .companyId(companyIds[i])
                        .firstName(names[i][j])
                        .lastName(lastNames.get(random.nextInt(lastNames.size()))) // Rastgele soyadı seçimi
                        .isMailVerified(true)
                        .email(names[i][j].toLowerCase() + "@company"+companyIds[i]+".com")
                        .gender(j % 2 == 0 ? EGender.MALE : EGender.FEMALE)
                        .password(EncryptionManager.getEncryptedPassword("sifre123"))
                        .build());
            }
        }

        return employees;
    }

    public static List<UserDetails> generateUserDetails() {
        List<UserDetails> userDetailsList = new ArrayList<>();

        // Company manager
        for (int i = 1; i <= 5; i++) {
            userDetailsList.add(UserDetails.builder()
                    .tcNo("26145691" + (850 + i))
                    .sgkNo("15462" + i)
                    .hireDate(LocalDate.of(2023, 1 + i, 15))
                    .userId((long) i)
                    .salary(120000.0 + (i * 5000))
                    .departmentType(EUserDepartmentType.ADMINISTRATION)
                    .birthDate(LocalDate.of(1980 + i, 3 + i, 10))
                    .addressId((long) i)
                    .build());
        }

        // Employee
        for (int i = 6; i <= 55; i++) {
            userDetailsList.add(UserDetails.builder()
                    .tcNo("84576634" + (354 + i))
                    .sgkNo("24879" + i)
                    .hireDate(LocalDate.of(2022 + (i % 3), (i % 12) + 1, (i % 28) + 1))
                    .userId((long) i)
                    .salary(45000.0 + (i % 10 * 2500))
                    .departmentType(getRandomDepartment())
                    .birthDate(LocalDate.of(1990 + (i % 5), (i % 12) + 1, (i % 28) + 1))
                    .addressId((long) i)
                    .build());
        }

        return userDetailsList;
    }

    private static EUserDepartmentType getRandomDepartment() {
        EUserDepartmentType[] departments = EUserDepartmentType.values();
        return departments[new Random().nextInt(departments.length)];
    }

    public static List<Company> generateCompany(){
        Company kocHolding = Company.builder().name("KOC HOLDING").addressId(56L).phone("05362065163").foundationDate(LocalDate.of(1981, 4, 12)).build();
        Company trumpHolding = Company.builder().name("TRUMP HOLDING").addressId(57L).phone("05414322685").foundationDate(LocalDate.of(1977,5,16)).build();
        Company apple = Company.builder().name("APPLE").addressId(58L).phone("05224442255").foundationDate(LocalDate.of(1987,5,16)).build();
        Company microsoft = Company.builder().name("MICROSOFT").addressId(59L).phone("05334567685").foundationDate(LocalDate.of(1969,3,26)).build();
        Company amazon = Company.builder().name("AMAZON").addressId(60L).phone("05362067164").foundationDate(LocalDate.of(1978,5,21)).build();
        return List.of(kocHolding, trumpHolding,apple,microsoft,amazon);
    }

    public static List<Address> generateAddress() {
        List<String> cities = List.of("İSTANBUL", "ANKARA", "İZMİR", "KOCAELİ", "SAMSUN", "NEW YORK", "LONDON", "PARIS", "BERLIN", "TOKYO");
        List<Address> addressList = new ArrayList<>();

        for (int i = 1; i <= 60; i++) {
            addressList.add(Address.builder()
                    .city(cities.get(new Random().nextInt(cities.size())))
                    .build());
        }

        return addressList;
    }




}
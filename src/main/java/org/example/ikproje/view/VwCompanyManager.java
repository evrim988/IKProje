package org.example.ikproje.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ikproje.entity.enums.EUserDepartmentType;
import org.example.ikproje.entity.enums.EUserRole;

import java.time.LocalDate;

public record VwCompanyManager(

                //user
                Long id,
                Long companyId,
                String firstName,
                String lastName,
                String email,
                String phone,
                String avatarUrl,
                EUserRole userRole,
                //userAdress
                String region,
                String city,
                String district,
                String neighbourhood,
                String street,
                String postalCode,
                String aptNumber,
                //userDetails
                LocalDate hireDate,
                String tcNo,
                String sgkNo,
                LocalDate birthDate,
                EUserDepartmentType userDepartmentType,
                //companyAddress
                String companyRegion,
                String companyCity,
                String companyDistrict,
                String companyNeighbourhood,
                String companyStreet,
                String companyPostalCode,
                String companyAptNumber,
                //company
                String companyLogoUrl,
                String companyName,
                String companyPhone,
                LocalDate companyFoundationDate,
                String companyIndustry

) {










}

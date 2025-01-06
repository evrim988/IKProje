package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.example.ikproje.entity.enums.EMembershipType;
import org.example.ikproje.entity.enums.EUserDepartmentType;

import java.time.LocalDate;

public record UpdateCompanyManagerProfileRequestDto(

        //user
        @NotNull
        String token,
        @NotNull
        Long id,
        String firstName,
        String lastName,
        String phone,
        String email,

        //userDetails
        Double salary,
        LocalDate hireDate,
        @Pattern(regexp = "\\d{11}", message = "TC No 11 haneli olmalıdır ve tamamı rakamlardan oluşmalıdır.")
        String tcNo,
        String sgkNo,
        LocalDate birthDate,
        EUserDepartmentType departmentType,
        //userAddress
        String region,
        String city,
        String district,
        String neighbourhood,
        String street,
        String postalCode,
        String aptNumber,
        //companyAddress
        String companyRegion,
        String companyCity,
        String companyDistrict,
        String companyNeighbourhood,
        String companyStreet,
        String companyPostalCode,
        String companyAptNumber,
        //company
        String companyName,
        String companyPhone,
        LocalDate companyFoundationDate,
        String companyIndustry




) {
}
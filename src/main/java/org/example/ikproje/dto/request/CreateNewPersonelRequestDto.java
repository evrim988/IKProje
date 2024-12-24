package org.example.ikproje.dto.request;

import org.example.ikproje.entity.enums.EGender;
import org.example.ikproje.entity.enums.EUserDepartmentType;

import java.time.LocalDate;

public record CreateNewPersonelRequestDto(

        String token, //şirket sahibi tokeni
        String firstName,
        String lastName,
        String email,
        String password,
        String rePassword,
        String phone,
        Double salary,
        String tcNo,
        String sgkNo,
		EGender gender,
        LocalDate birthDate,
        LocalDate hireDate,
        EUserDepartmentType departmentType


) {
}
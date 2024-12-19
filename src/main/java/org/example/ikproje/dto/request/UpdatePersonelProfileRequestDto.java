package org.example.ikproje.dto.request;

import jakarta.validation.constraints.Pattern;
import org.example.ikproje.entity.enums.EUserDepartmentType;
import org.example.ikproje.entity.enums.EUserRole;

import java.time.LocalDate;

public record UpdatePersonelProfileRequestDto(
        //companyName ve userRole gibi personelin değiştirimeyeceği alanları koymadım buraya.
        String token,
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String avatarUrl,
        //adres
        String region,
        String city,
        String district,
        String neighbourhood,
        String street,
        String postalCode,
        String aptNumber,
        //userDetails
        LocalDate hireDate,
        @Pattern(regexp = "\\d{11}", message = "TC No 11 haneli olmalıdır ve tamamı rakamlardan oluşmalıdır.")
        String tcNo,
        String sgkNo,
        LocalDate birthDate,
        EUserDepartmentType departmentType

) {
}

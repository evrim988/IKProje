package org.example.ikproje.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.ikproje.entity.enums.EUserDepartmentType;

import java.time.LocalDate;

public record UserRegisterRequestDto(
		String firstName,
		String lastName,
		String email,
		String password,
		String rePassword,
		String phone,
		String avatarUrl,
		Double salary,
		LocalDate hireDate,
		@Pattern(regexp = "\\d{11}", message = "TC No 11 haneli olmalıdır ve tamamı rakamlardan oluşmalıdır.")
		String tcNo,
		String sgkNo,
		LocalDate birthDate,
		EUserDepartmentType departmentType,
		String region,
		String city,
		String district,
		String neighborhood,
		String street,
		String postalCode,
		String aptNumber,
		String companyRegion,
		String companyCity,
		String companyDistrict,
		String companyNeighborhood,
		String companyStreet,
		String companyPostalCode,
		String companyAptNumber,
		String companyName,
		String companyPhone,
		String companyEmail,
		String companyLogo,
		LocalDate companyFoundationDate,
		String companyIndustry
		) {
}
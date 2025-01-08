package org.example.ikproje.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.ikproje.entity.enums.EGender;
import org.example.ikproje.entity.enums.EMembershipType;
import org.example.ikproje.entity.enums.EUserDepartmentType;

import java.time.LocalDate;

public record RegisterRequestDto(
		@NotNull
		String firstName,
		@NotNull
		String lastName,
		@NotNull
		String phone,
		@NotNull
		Double salary,
		@NotNull
		LocalDate hireDate,
		@Pattern(regexp = "\\d{11}", message = "TC No 11 haneli olmalıdır ve tamamı rakamlardan oluşmalıdır.")
		String tcNo,
		@NotNull
		String sgkNo,
		@NotNull
		LocalDate birthDate,
		@NotNull
		EGender gender,
		@NotNull
		EUserDepartmentType departmentType,
		@NotNull
		String region,
		@NotNull
		String city,
		@NotNull
		String district,
		@NotNull
		String neighbourhood,
		@NotNull
		String street,
		@NotNull
		String postalCode,
		@NotNull
		String aptNumber,
		@NotNull
		String companyRegion,
		@NotNull
		String companyCity,
		@NotNull
		String companyDistrict,
		@NotNull
		String companyNeighbourhood,
		@NotNull
		String companyStreet,
		@NotNull
		String companyPostalCode,
		@NotNull
		String companyAptNumber,
		@NotNull
		String companyName,
		@NotNull
		String companyPhone,
		@NotNull
		@Email
		String email,
		@NotNull
		@Size(min=8,max=64)
		@Pattern(
				message = "Şifreniz en az 8, en fazla 64 karakter olmalıdır. Şifrenizde en az bir büyük harf, bir küçük " +
						"harf ve özel karakter olmalıdır.",
				regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$"
		)
		String password,
		@NotNull
		String rePassword,
		@NotNull
		LocalDate companyFoundationDate,
		@NotNull
		String companyIndustry,
		@NotNull
		EMembershipType membershipType
		) {
}
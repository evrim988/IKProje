package org.example.ikproje.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.ikproje.entity.enums.EGender;
import org.example.ikproje.entity.enums.EUserDepartmentType;

import java.time.LocalDate;

public record CreateNewPersonelRequestDto(
		@NotNull
        String token, //şirket sahibi tokeni
		@NotNull
        String firstName,
		@NotNull
        String lastName,
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
        String phone,
		@NotNull
        Double salary,
		@NotNull
		@Pattern(regexp = "\\d{11}", message = "TC No 11 haneli olmalıdır ve tamamı rakamlardan oluşmalıdır.")
        String tcNo,
		@NotNull
        String sgkNo,
		@NotNull
		EGender gender,
		@NotNull
        LocalDate birthDate,
		@NotNull
        LocalDate hireDate,
		@NotNull
        EUserDepartmentType departmentType


) {
}
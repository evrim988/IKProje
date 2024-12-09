package org.example.ikproje.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDto(
//		@NotNull
//		@Size(min=3,max=40)
		String firstName,
//		@NotNull
//		@Size(min=3,max=40)
		String lastName,
//		@Email
		String email,
//		@NotNull
//		@Size(min=3,max=40)
		String username,
//		@NotNull
//		@Size(min=8,max=64)
//		@Pattern(
//				message = "Şifreniz en az 8, en fazla 64 karakter olmalıdır. Şifrenizde en az bir büyük harf, bir küçük " +
//						"harf ve özel karakter olmalıdır.",
//				regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$"
//		)
		String password,
		String rePassword,
//		@NotNull
		String birthDate,
//		@NotNull
		Double salary,
//		@NotNull
		String region,
//		@NotNull
		String address,
//		@NotNull
		String phone,
//		@NotNull
		String avatarUrl,
//		@NotNull
//		@Size(min=3,max=40)
		String companyName,
//		@NotNull
		String companyAddress,
//		@NotNull
		String companyPhone,
//		@Email
		String companyEmail,
//		@NotNull
		String companyLogo,
//		@NotNull
		Long foundationDate
) {
}
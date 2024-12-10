package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
//		@NotNull
//		@Size(min=3,max=40)
		String email,
//		@NotNull
//		@Size(min=8,max=64)
//		@Pattern(
//				message = "Şifreniz en az 8, en fazla 64 karakter olmalıdır. Şifrenizde en az bir büyük harf, bir küçük " +
//						"harf ve özel karakter olmalıdır.",
//				regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$"
//		)
		String password
) {
}
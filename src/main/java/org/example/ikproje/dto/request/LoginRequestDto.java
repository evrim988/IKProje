package org.example.ikproje.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
		@NotNull
		@Email
		String email,
		@NotNull
		String password
) {
}
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
		String phone
		) {
}
package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AssignShiftToUserRequestDto(
		@NotNull
		String token,
		@NotNull
		Long shiftId,
		@NotNull
		Long userId,
		@NotNull
		LocalDate startDate,
		@NotNull
		LocalDate endDate
) {
}
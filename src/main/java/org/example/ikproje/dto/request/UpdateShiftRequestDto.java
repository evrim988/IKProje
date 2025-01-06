package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateShiftRequestDto(
		@NotNull
		String token,
		@NotNull
		Long shiftId,
		String name,
		String startTime,
		String endTime
) {
}
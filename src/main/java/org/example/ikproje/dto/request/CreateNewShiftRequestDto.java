package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record CreateNewShiftRequestDto(
		@NotNull
		String token,
		@NotNull
		String name,
		@NotNull
		String startTime,
		@NotNull
		String endTime
) {
}
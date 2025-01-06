package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateNewBreakRequestDto(
		@NotNull
		String token,
		@NotNull
		String name,
		@NotNull
		String startTime,
		@NotNull
		String endTime,
		@NotNull
		Long shiftId
) {
}
package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateBreakRequestDto(
		@NotNull
		String token,
		@NotNull
		Long breakId,
		String name,
		String startTime,
		String endTime,
		Long shiftId
		
) {
}
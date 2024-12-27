package org.example.ikproje.dto.request;

public record UpdateBreakRequestDto(
		String token,
		Long breakId,
		String name,
		String startTime,
		String endTime,
		Long shiftId
		
) {
}
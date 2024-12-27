package org.example.ikproje.dto.request;

public record CreateNewBreakRequestDto(
		String token,
		String name,
		String startTime,
		String endTime,
		Long shiftId
) {
}
package org.example.ikproje.dto.request;

public record UpdateShiftRequestDto(
		String token,
		Long shiftId,
		String name,
		String startTime,
		String endTime
) {
}
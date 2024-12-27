package org.example.ikproje.dto.request;

import java.time.LocalTime;

public record CreateNewShiftRequestDto(
		String token,
		String name,
		String startTime,
		String endTime
) {
}
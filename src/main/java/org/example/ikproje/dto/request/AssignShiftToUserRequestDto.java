package org.example.ikproje.dto.request;

import java.time.LocalDate;

public record AssignShiftToUserRequestDto(
		String token,
		Long shiftId,
		Long userId,
		LocalDate startDate,
		LocalDate endDate
) {
}
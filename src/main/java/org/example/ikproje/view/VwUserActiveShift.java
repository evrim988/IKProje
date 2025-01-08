package org.example.ikproje.view;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record VwUserActiveShift(
		Long userId,
		String shiftName,
		LocalTime shiftStartTime,
		LocalTime shiftEndTime,
		LocalDate startDate,
		LocalDate endDate,
		List<VwBreakSummary> breaks
) {
}
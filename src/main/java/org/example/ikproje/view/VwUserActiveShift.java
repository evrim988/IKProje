package org.example.ikproje.view;

import java.time.LocalTime;
import java.util.List;

public record VwUserActiveShift(
		Long userId,
		String shiftName,
		LocalTime shiftStartTime,
		LocalTime shiftEndTime,
		List<VwBreakSummary> breaks
) {
}
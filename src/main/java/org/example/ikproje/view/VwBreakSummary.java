package org.example.ikproje.view;

import java.time.LocalTime;

public record VwBreakSummary(
		String breakName,
		LocalTime breakStartTime,
		LocalTime breakEndTime
) {
}
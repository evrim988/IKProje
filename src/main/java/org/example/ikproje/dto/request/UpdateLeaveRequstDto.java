package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.ikproje.entity.enums.ELeaveType;

import java.time.LocalDate;

public record UpdateLeaveRequstDto(
		@NotNull
        String token,
		@NotNull
        Long leaveId,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        ELeaveType leaveType
) {
}
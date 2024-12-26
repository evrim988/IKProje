package org.example.ikproje.dto.request;

import org.example.ikproje.entity.enums.ELeaveType;

import java.time.LocalDate;

public record UpdateLeaveRequstDto(

        String token,
        Long leaveId,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        ELeaveType leaveType
) {
}

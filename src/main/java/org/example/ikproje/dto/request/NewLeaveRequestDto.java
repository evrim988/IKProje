package org.example.ikproje.dto.request;

import org.example.ikproje.entity.enums.ELeaveType;

import java.time.LocalDate;

public record NewLeaveRequestDto(
        String token,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        ELeaveType leaveType

) {

}
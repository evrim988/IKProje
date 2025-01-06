package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.ikproje.entity.enums.ELeaveType;

import java.time.LocalDate;

public record NewLeaveRequestDto(
		@NotNull
        String token,
		@NotNull
        String description,
		@NotNull
        LocalDate startDate,
		@NotNull
        LocalDate endDate,
		@NotNull
        ELeaveType leaveType

) {

}
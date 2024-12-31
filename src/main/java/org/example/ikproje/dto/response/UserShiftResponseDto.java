package org.example.ikproje.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserShiftResponseDto(
        Long id,
        String shiftName,
        String personelName,
        String personelSurname,
        LocalDate startDate,
        LocalDate endDate
) {
}

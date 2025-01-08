package org.example.ikproje.dto.response;

import lombok.Builder;
import org.example.ikproje.view.VwBreakSummary;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UserShiftResponseDto(
        Long id,
        String shiftName,
        String personelName,
        String personelSurname,
        LocalDate startDate,
        LocalDate endDate,
        List<VwBreakSummary> breaks
) {
}

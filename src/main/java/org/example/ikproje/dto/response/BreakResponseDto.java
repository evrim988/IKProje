package org.example.ikproje.dto.response;

import lombok.Builder;

@Builder
public record BreakResponseDto(
        Long id,
        String name,
        String startTime,
        String endTime,
        String shiftName
) {
}

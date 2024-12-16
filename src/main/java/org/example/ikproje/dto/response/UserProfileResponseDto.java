package org.example.ikproje.dto.response;

import lombok.Builder;
import org.example.ikproje.entity.enums.EUserRole;

@Builder
public record UserProfileResponseDto(
        String avatarUrl,
        String firstName,
        String lastName,
        EUserRole userRole,
        String companyName
) {
}

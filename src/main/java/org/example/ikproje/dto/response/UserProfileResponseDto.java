package org.example.ikproje.dto.response;

import lombok.Builder;

@Builder
public record UserProfileResponseDto(
        String avatarUrl,
        String firstName,
        String lastName
) {
}

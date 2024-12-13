package org.example.ikproje.dto.response;

import lombok.Builder;

@Builder
public record UserProfileResonseDto(
        String avatarUrl,
        String firstName,
        String lastName
) {
}

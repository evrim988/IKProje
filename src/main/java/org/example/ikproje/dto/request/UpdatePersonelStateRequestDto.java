package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.ikproje.entity.enums.EState;

public record UpdatePersonelStateRequestDto(
		@NotNull
        String token,
		@NotNull
        Long personelId,
        EState stateToChange

) {
}
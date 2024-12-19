package org.example.ikproje.dto.request;

import org.example.ikproje.entity.enums.EState;

public record UpdatePersonelStateRequestDto(

        String token,
        Long personelId,
        EState stateToChange

) {
}

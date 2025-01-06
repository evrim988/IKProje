package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.ikproje.entity.enums.EAssetType;

public record NewAssetRequestDto(
		@NotNull
        String token,
		@NotNull
        Long personalId,
		@NotNull
        String description,
		@NotNull
        EAssetType assetType

) {
}
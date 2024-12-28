package org.example.ikproje.dto.request;

import org.example.ikproje.entity.enums.EAssetType;

public record NewAssetRequestDto(

        String token,
        Long personalId,
        String description,
        EAssetType assetType

) {
}

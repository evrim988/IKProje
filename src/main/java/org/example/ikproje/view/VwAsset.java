package org.example.ikproje.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.ikproje.entity.enums.EAssetStatus;
import org.example.ikproje.entity.enums.EAssetType;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public record VwAsset(
        Long id,
        String assetOwner, //bu assete hangi üye sahip
        String assetGiver, //bu asseti hangi yönetici atamış
        LocalDate givenDate,
        String description,
        EAssetStatus status,
        EAssetType assetType
) {

}

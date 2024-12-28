package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.ikproje.entity.enums.EAssetStatus;
import org.example.ikproje.entity.enums.EAssetType;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblAsset")
public class Asset extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long personelId;
    Long companyManagerId;
    String description; //verilen zimmet ile ilgili açıklama. marka model vs. ne için verildiği
    @Builder.Default
    LocalDate givenDate = LocalDate.now();
    @Builder.Default
    @Enumerated(EnumType.STRING)
    EAssetStatus status = EAssetStatus.PENDING;
    @Enumerated(EnumType.STRING)
    EAssetType assetType;


}

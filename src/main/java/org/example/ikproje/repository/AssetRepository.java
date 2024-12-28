package org.example.ikproje.repository;

import org.example.ikproje.entity.Asset;
import org.example.ikproje.view.VwAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT new org.example.ikproje.view.VwAsset(a.id,CONCAT(p.firstName,' ',p.lastName) ,CONCAT(cm.firstName,' ',cm.lastName),a.givenDate,a.description,a.status,a.assetType) from Asset a JOIN User cm ON cm.id=a.companyManagerId JOIN User p ON a.personelId=p.id WHERE a.personelId=?1 AND a.state = org.example.ikproje.entity.enums.EState.ACTIVE")
    List<VwAsset> getAllVwAssetsByUserId(Long userId);
}

package org.example.ikproje.repository;

import org.example.ikproje.entity.Asset;
import org.example.ikproje.view.VwAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT new org.example.ikproje.view.VwAsset(a.id,a.name,a.description) from Asset a")
    List<VwAsset> getAllVwAssetsByUserId(Long userId);
}

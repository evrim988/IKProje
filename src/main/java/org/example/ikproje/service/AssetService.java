package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.repository.AssetRepository;
import org.example.ikproje.view.VwAsset;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public List<VwAsset> getAllVwAssetsByUserId(Long userId){
        return assetRepository.getAllVwAssetsByUserId(userId);
    }
}

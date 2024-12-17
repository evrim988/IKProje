package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Asset;
import org.example.ikproje.entity.User;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.AssetRepository;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwAsset;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final JwtManager jwtManager;


    public List<VwAsset> getAllVwAssetsByUserId(Long userId){
        return assetRepository.getAllVwAssetsByUserId(userId);
    }
}
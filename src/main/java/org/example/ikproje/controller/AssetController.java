package org.example.ikproje.controller;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.AssetService;
import org.example.ikproje.view.VwAsset;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ASSET)
@CrossOrigin("*")
public class AssetController {
    private final AssetService assetService;

    @GetMapping(GET_PERSONEL_ASSETS)
    public ResponseEntity<BaseResponse<List<VwAsset>>> getAllPersonelAssets(String token){
        return ResponseEntity.ok(BaseResponse.<List<VwAsset>>builder()
                        .success(true)
                        .message("Personel zimmet listesi")
                        .success(true)
                        .data(assetService.getAllPersonetAssets(token))
                .build());
    }


}
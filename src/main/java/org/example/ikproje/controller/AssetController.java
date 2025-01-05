package org.example.ikproje.controller;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.NewAssetRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.AssetService;
import org.example.ikproje.view.VwAsset;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ASSET)
@CrossOrigin("*")
public class AssetController {
    private final AssetService assetService;


    //personel üzerine atanan assetleri görüyor
    @GetMapping(GET_PERSONEL_ASSETS)
    public ResponseEntity<BaseResponse<List<VwAsset>>> getPersonelAssets(@RequestParam String token) {
        return ResponseEntity.ok(BaseResponse.<List<VwAsset>>builder()
                .success(true)
                .message("Şirket personellerinin zimmet listesi")
                .data(assetService.getAllPersonelAssets(token))
                .build());
    }


    //Şirket yöneticisi için, bütün personel assetlerini görüyor
    @GetMapping(GET_ASSETS_OF_COMPANY)
    public ResponseEntity<BaseResponse<List<VwAsset>>> getAssetListOfCompany(@RequestParam String token) {
        return ResponseEntity.ok(BaseResponse.<List<VwAsset>>builder()
                .success(true)
                .message("Personel zimmet listesi")
                .data(assetService.getAssetListOfCompany(token))
                .build());
    }

    @PostMapping(ASSIGN_NEW_ASSET)
    public ResponseEntity<BaseResponse<Boolean>> assignNewAsset(@RequestBody NewAssetRequestDto dto){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .message("İşlem başarılı")
                        .success(assetService.assignNewAssetToPersonel(dto))
                        .code(200)
                .build());
    }

    @PutMapping(APPROVE_ASSET)
    public ResponseEntity<BaseResponse<Boolean>> approveAsset(@RequestParam String token,@RequestParam Long assetId){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .message("Zimmet atanması onaylandı.")
                .success(assetService.approveAssetAssignment(token,assetId))
                .code(200)
                .build());
    }

    @PutMapping(REJECT_ASSET)
    public ResponseEntity<BaseResponse<Boolean>> rejectAsset(@RequestParam String token,@RequestParam Long assetId,String rejectMessage){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .message("Zimmet atanması reddedildi.")
                .success(assetService.rejectAssetAssignment(token,assetId,rejectMessage))
                .code(200)
                .build());
    }


}
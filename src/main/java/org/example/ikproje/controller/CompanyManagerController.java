package org.example.ikproje.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.CreateNewPersonelRequestDto;
import org.example.ikproje.dto.request.UpdateCompanyManagerProfileRequestDto;
import org.example.ikproje.dto.request.UpdatePersonelStateRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.CompanyManagerService;
import org.example.ikproje.view.VwCompanyManager;
import org.example.ikproje.view.VwPersonelSummary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMPANY_MANAGER)
@CrossOrigin("*")
public class CompanyManagerController {

    private final CompanyManagerService companyManagerService;

    @PostMapping(value = UPDATE_COMPANY_LOGO,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> addLogoToCompany(@RequestParam String token,
                                                                  @RequestParam MultipartFile file)
            throws IOException {
        companyManagerService.addLogoToCompany(token,file);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .success(true)
                .message("Şirket logosu eklendi.")
                .build());
    }
    @GetMapping("/get-company-manager-profile")
    public ResponseEntity<BaseResponse<VwCompanyManager>> getCompanyManagerProfile(@RequestParam String token){
        return ResponseEntity.ok(BaseResponse.<VwCompanyManager>builder()
                .code(200)
                .data(companyManagerService.getCompanyManagerProfile(token))
                .success(true)
                .message("Şirket yöneticisi profili getirildi.")
                .build());
    }

    @PostMapping(ADD_PERSONEL)
    public ResponseEntity<BaseResponse<Boolean>> addNewPersonel(@RequestBody @Valid CreateNewPersonelRequestDto dto){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(companyManagerService.addNewPersonel(dto))
                .message("Yeni personel eklendi.")
                .code(200)
                .build());
    }

    @PostMapping(UPDATE_PERSONEL_STATE)
    public ResponseEntity<BaseResponse<Boolean>> updatePersonelState(@RequestBody @Valid UpdatePersonelStateRequestDto dto){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(companyManagerService.updatePersonalState(dto))
                .message("Personel aktiflik durumu güncellendi.")
                .code(200)
                .build());
    }

    //Personel özet bilgilerini liste halinde getiren endpoint, şirket yöneticisi için
    @GetMapping(GET_PERSONEL_LIST)
    public ResponseEntity<BaseResponse<List<VwPersonelSummary>>> getPersonelList(@RequestParam String token){
        return ResponseEntity.ok(BaseResponse.<List<VwPersonelSummary>>builder()
                .data(companyManagerService.getPersonelList(token))
                .success(true)
                .message("Şirket personel listesi getirildi.")
                .code(200)
                .build());
    }

    @PutMapping("/update-company-manager-profile")
    public ResponseEntity<BaseResponse<Boolean>> updateCompanyManagerProfile(@RequestBody @Valid UpdateCompanyManagerProfileRequestDto dto){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(companyManagerService.updateCompanyManagerProfile(dto))
                .message("Yönetici profili güncellendi.")
                .code(200)
                .build());
    }
}

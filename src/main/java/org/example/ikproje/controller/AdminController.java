package org.example.ikproje.controller;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.entity.Admin;
import org.example.ikproje.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN)
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(BaseResponse.<String>builder()
                        .code(200)
                        .success(true)
                        .message("Merhaba Admin")
                        .data(adminService.login(dto))
                .build());
    }


}

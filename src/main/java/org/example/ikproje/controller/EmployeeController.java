package org.example.ikproje.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.UpdatePersonelProfileRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.EmployeeService;
import org.example.ikproje.view.VwPersonel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EMPLOYEE)
@CrossOrigin("*")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/get-personel-profile")
    public ResponseEntity<BaseResponse<VwPersonel>> getPersonelProfile(@RequestParam String token){
        return ResponseEntity.ok(BaseResponse.<VwPersonel>builder()
                .code(200)
                .data(employeeService.getPersonelProfile(token))
                .success(true)
                .message("Personel profili getirildi.")
                .build());
    }


    @PutMapping("/update-personel-profile")
    public ResponseEntity<BaseResponse<Boolean>> updatePersonelProfile(@RequestBody @Valid UpdatePersonelProfileRequestDto dto){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(employeeService.updatePersonelProfile(dto))
                .message("Kullanıcı profili güncellendi.")
                .code(200)
                .build());
    }
}

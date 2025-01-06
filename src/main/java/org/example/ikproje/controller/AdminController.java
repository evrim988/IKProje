package org.example.ikproje.controller;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.enums.EIsApproved;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.service.AdminService;
import org.example.ikproje.service.UserService;
import org.example.ikproje.view.VwUnapprovedAccounts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN)
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @PostMapping(LOGIN)
    public ResponseEntity<BaseResponse<String>> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(BaseResponse.<String>builder()
                        .code(200)
                        .success(true)
                        .message("Merhaba Admin")
                        .data(adminService.login(dto))
                .build());
    }
    
    @PutMapping(APPROVE_ACCOUNT)
    public ResponseEntity<BaseResponse<Boolean>> approveAccount(Long userId, String confirmationMessage){
        adminService.approveAccount(userId,confirmationMessage);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                                         .code(200)
                                         .success(true)
                                         .data(true)
                                         .message("Kayıt onaylandı.")
                                             .build());
    }
    @PutMapping(REJECT_ACCOUNT)
    public ResponseEntity<BaseResponse<Boolean>> rejectAccount(Long userId,String rejectionMessage ){
        adminService.rejectAccount(userId,rejectionMessage);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                                         .code(200)
                                         .success(true)
                                         .data(true)
                                         .message("Kayıt onayı başarıyla reddedildi.")
                                             .build());
    }
    @GetMapping(GET_UNAPPROVED_COMPANIES)
    public ResponseEntity<BaseResponse<List<VwUnapprovedAccounts>>> getUnapprovedCompanies() {
        return ResponseEntity.ok(BaseResponse.<List<VwUnapprovedAccounts>>builder()
                                         .code(200)
                                             .success(true)
                                         .message("Onaylanmamış şirketler getirildi.")
                                         .data(adminService.getAllUnapprovedCompanies())
                                             .build());
    }
    
    @GetMapping(COMPANY_COUNT)
    public ResponseEntity<BaseResponse<Long>> getCompanyCount() {
        return ResponseEntity.ok(BaseResponse.<Long>builder()
                                         .code(200)
                                         .data(adminService.activeCompanyCount())
                                         .message("Aktif şirket sayısı getirildi.")
                                         .success(true)
                                         .build());
    }
    
    @GetMapping(EMPLOYEE_COUNT)
    public ResponseEntity<BaseResponse<Long>> getEmployeeCount() {
        return ResponseEntity.ok(BaseResponse.<Long>builder()
                                             .code(200)
                                             .data(adminService.activeEmployeeCount())
                                             .message("Aktif personel sayısı getirildi.")
                                             .success(true)
                                             .build());
    }
    @GetMapping(EXPIRING_MEMBERSHIPS)
    public ResponseEntity<BaseResponse<List<Company>>> getCompaniesWithExpiringMemberships(){
        return ResponseEntity.ok(BaseResponse.<List<Company>>builder()
                                         .code(200)
                                         .success(true)
                                         .message("Üyeliğinin bitmesine 1 aydan az kalmış şirketler getirildi.")
                                         .data(adminService.getCompaniesWithExpiringMemberships())
                                         .build());
    }
}
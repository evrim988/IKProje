package org.example.ikproje.controller;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.NewLeaveRequestDto;
import org.example.ikproje.dto.request.UpdateLeaveRequstDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.entity.Leave;
import org.example.ikproje.service.LeaveService;
import org.example.ikproje.view.VwAllPersonelLeaveList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(LEAVE)
@CrossOrigin("*")
public class LeaveController {
    private final LeaveService leaveService;

    @PostMapping(NEW_LEAVE_REQUEST)
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<BaseResponse<Boolean>> createNewLeaveRequest(@RequestBody NewLeaveRequestDto dto){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .message("Yeni izin isteği oluşturuldu.")
                        .success(leaveService.createNewLeaveRequest(dto))
                .build());
    }

    //Şirket yöneticisi için
    @GetMapping(GET_LEAVE_REQUEST)
    @PreAuthorize("hasAuthority('COMPANY_MANAGER')")
    public ResponseEntity<BaseResponse<List<VwAllPersonelLeaveList>>> getLeaveRequests(@RequestParam String token){
        return ResponseEntity.ok(BaseResponse.<List<VwAllPersonelLeaveList>>builder()
                .code(200)
                .message("Personel izin istekleri listesi.")
                .success(true)
                .data(leaveService.getAllLeaveRequests(token))
                .build());
    }

    //personelin yaptığı izin istekleri için
    @GetMapping(GET_PERSONEL_REQUEST_LEAVE)
    public ResponseEntity<BaseResponse<List<Leave>>> getPersonelRequestLeaveList(@RequestParam String token){
        return ResponseEntity.ok(BaseResponse.<List<Leave>>builder()
                        .code(200)
                        .message("Personel talepte bulunduğu izin istekleri listesi.")
                        .success(true)
                        .data(leaveService.getPersonelRequestLeaveList(token))
                .build());
    }

    @PostMapping(APPROVE_LEAVE_REQUEST)
    @PreAuthorize("hasAuthority('COMPANY_MANAGER')")
    public ResponseEntity<BaseResponse<Boolean>> approveLeaveRequest(@RequestParam String token,@RequestParam Long leaveId){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .success(leaveService.approveLeaveRequest(token,leaveId))
                        .code(200)
                        .message("Personel izin isteği onaylandı.")
                .build());
    }

    @PostMapping(REJECT_LEAVE_REQUEST)
    @PreAuthorize("hasAuthority('COMPANY_MANAGER')")
    public ResponseEntity<BaseResponse<Boolean>> rejectLeaveRequest(@RequestParam String token,
                                                                    @RequestParam Long leaveId,
                                                                    @RequestParam String rejectionMessage){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(leaveService.rejectLeaveRequest(token,leaveId,rejectionMessage))
                .code(200)
                .message("Personel izin isteği reddedildi.")
                .build());
    }

    @PutMapping(UPDATE_LEAVE_REQUEST)
    public ResponseEntity<BaseResponse<Boolean>> updateLeaveRequest(@RequestBody UpdateLeaveRequstDto dto){
        Boolean success = leaveService.updatePersonelLeaveRequest(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .success(success)
                        .message(success ? "İzin güncelleme başarılı" : "İzin güncelleme gerçekleştirilemedi.")
                .build());
    }

    @DeleteMapping(DELETE_LEAVE_REQUEST)
    public ResponseEntity<BaseResponse<Boolean>> deleteLeaveRequest(@RequestParam String token,@RequestParam Long leaveId){
        Boolean success  = leaveService.deletePersonelLeaveRequest(token,leaveId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(success)
                .message(success ? "İzin isteği başarı ile silindi" : "Silme işlemi gerçekleştirilemedi.")
                .build());
    }




}
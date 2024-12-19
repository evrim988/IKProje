package org.example.ikproje.controller;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.NewLeaveRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.entity.Leave;
import org.example.ikproje.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(LEAVE)
@CrossOrigin("*")
public class LeaveController {
    private final LeaveService leaveService;

    @PostMapping("/new-leave-request")
    public ResponseEntity<BaseResponse<Boolean>> createNewLeaveRequest(@RequestBody NewLeaveRequestDto dto){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .message("Yeni izin isteği oluşturuldu.")
                        .success(leaveService.createNewLeaveRequest(dto))
                .build());
    }

    @GetMapping("/get-leave-requests")
    public ResponseEntity<BaseResponse<List<Leave>>> getLeaveRequests(@RequestParam String token){
        return ResponseEntity.ok(BaseResponse.<List<Leave>>builder()
                .code(200)
                .message("Personel izin istekleri listesi.")
                .success(true)
                .data(leaveService.getAllLeaveRequests(token))
                .build());
    }

    @PostMapping("approve-leave-request")
    public ResponseEntity<BaseResponse<Boolean>> approveLeaveRequest(@RequestParam String token,@RequestParam Long leaveId){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .success(leaveService.approveLeaveRequest(token,leaveId))
                        .code(200)
                        .message("Personel izin isteği onaylandı.")
                .build());
    }

    @PostMapping("reject-leave-request")
    public ResponseEntity<BaseResponse<Boolean>> rejectLeaveRequest(@RequestParam String token,@RequestParam Long leaveId){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(leaveService.rejectLeaveRequest(token,leaveId))
                .code(200)
                .message("Personel izin isteği onaylandı.")
                .build());
    }




}

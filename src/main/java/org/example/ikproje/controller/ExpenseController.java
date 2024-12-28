package org.example.ikproje.controller;


import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.NewExpenseRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.ExpenseService;
import org.example.ikproje.view.VwExpense;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EXPENSE)
@CrossOrigin("*")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("get-personel-expenses")
    public ResponseEntity<BaseResponse<List<VwExpense>>> getPersonelExpenses(String token) {
        return ResponseEntity.ok(BaseResponse.<List<VwExpense>>builder()
                        .code(200)
                        .data(expenseService.getPersonelsExpenseList(token))
                        .success(true)
                        .message("Personel harcamaları listesi.")
                .build());
    }

    @GetMapping("get-personel-expense-requests")
    public ResponseEntity<BaseResponse<List<VwExpense>>> getPersonelExpenseRequests(String token) {
        return ResponseEntity.ok(BaseResponse.<List<VwExpense>>builder()
                .code(200)
                .data(expenseService.getAllExpenseRequestlist(token))
                .success(true)
                .message("Şirket içi harcamalar için yapılan istekler.")
                .build());
    }

    @PostMapping("create-new-expense-request")
    public ResponseEntity<BaseResponse<Boolean>> createNewExpenseRequest(NewExpenseRequestDto dto) {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .message("Yeni harcama kaydı isteği oluşturuldu.")
                        .code(200)
                        .success(expenseService.createNewExpenseRequest(dto))
                .build());
    }

    @PutMapping("approve-expense")
    public ResponseEntity<BaseResponse<Boolean>> approveExpense(String token, Long expenseId) {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .message("Personel harcama isteği onaylandı.")
                .code(200)
                .success(expenseService.approveExpenseRequest(token,expenseId))
                .build());
    }

    @PutMapping("reject-expense")
    public ResponseEntity<BaseResponse<Boolean>> rejectExpense(String token, Long expenseId) {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .message("Personel harcama isteği reddedildi..")
                .code(200)
                .success(expenseService.rejectExpenseRequest(token,expenseId))
                .build());
    }








}

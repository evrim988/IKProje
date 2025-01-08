package org.example.ikproje.controller;


import lombok.RequiredArgsConstructor;

import org.example.ikproje.dto.request.UpdateExpenseRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.ExpenseService;
import org.example.ikproje.view.VwExpense;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EXPENSE)
@CrossOrigin("*")
public class  ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping(GET_PERSONEL_EXPENSES)
    public ResponseEntity<BaseResponse<List<VwExpense>>> getPersonelExpenses(String token) {
        return ResponseEntity.ok(BaseResponse.<List<VwExpense>>builder()
                        .code(200)
                        .data(expenseService.getPersonelsExpenseList(token))
                        .success(true)
                        .message("Personel harcamaları listesi.")
                .build());
    }

    @GetMapping(GET_PERSONEL_EXPENSE_REQUESTS)
    public ResponseEntity<BaseResponse<List<VwExpense>>> getPersonelExpenseRequests(String token) {
        return ResponseEntity.ok(BaseResponse.<List<VwExpense>>builder()
                .code(200)
                .data(expenseService.getAllExpenseRequestlist(token))
                .success(true)
                .message("Şirket içi harcamalar için yapılan istekler.")
                .build());
    }

    @PostMapping(value = CREATE_NEW_EXPENSE_REQUEST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> createNewExpenseRequest(@RequestParam String token,
                                                                         @RequestParam Double amount,
                                                                         @RequestParam String description,
                                                                         @RequestParam MultipartFile file)
		    throws IOException {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .message("Yeni harcama kaydı isteği oluşturuldu.")
                        .code(200)
                        .success(true)
                        .data(expenseService.createNewExpenseRequest(token,amount,description,file))
                .build());
    }

    @PutMapping(APPROVE_EXPENSE)
    public ResponseEntity<BaseResponse<Boolean>> approveExpense(String token, Long expenseId) {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .message("Personel harcama isteği onaylandı.")
                .code(200)
                .success(true)
                .data(expenseService.approveExpenseRequest(token,expenseId))
                .build());
    }

    @PutMapping(REJECT_EXPENSE)
    public ResponseEntity<BaseResponse<Boolean>> rejectExpense(String token, Long expenseId, String rejectReason) {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .message("Personel harcama isteği reddedildi..")
                .code(200)
                .success(true)
                .data(expenseService.rejectExpenseRequest(token,expenseId,rejectReason))
                .build());
    }

    @PutMapping(DELETE_EXPENSE)
    public ResponseEntity<BaseResponse<Boolean>> deleteExpense(String token,Long expenseId){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                                         .code(200)
                                         .data(expenseService.deleteExpense(token,expenseId))
                                         .success(true)
                                         .message("Harcama isteği başarıyla silindi.")
                                         .build());

    }

    @PostMapping(value = UPLOAD_RECEIPT,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> uploadReceipt(@RequestParam String token,
                                                               @RequestParam Long expenseId,
                                                               @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .message("Fiş fotoğrafı yüklendi.")
                .code(200)
                .success(true)
                .data(expenseService.addReceiptPhotoToExpense(token,expenseId,file))
                .build());
    }

    @PutMapping(value = UPDATE_EXPENSE,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> updateExpense(@RequestParam String token,
                                                               @RequestParam Long expenseId,
                                                               @RequestParam Double amount,
                                                               @RequestParam String description,
                                                               @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .message("Harcama isteği güncellendi.")
                .code(200)
                .success(true)
                .data(expenseService.updateExpense(token,expenseId,amount,description,file))
                .build());
    }









}
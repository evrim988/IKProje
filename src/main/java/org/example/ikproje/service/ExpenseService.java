package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.NewExpenseRequestDto;
import org.example.ikproje.dto.request.UpdateExpenseRequestDto;
import org.example.ikproje.entity.Expense;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserDetails;
import org.example.ikproje.entity.enums.EExpenseStatus;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.ExpenseRepository;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwExpense;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final UserDetailsService userDetailsService;


    //personelin yapmış olduğu tüm istekler, reddedilmiş kabul edilmiş ve beklemede olanlar
    public List<VwExpense> getPersonelsExpenseList(String token){
        User personel = userService.getUserByToken(token);
        return expenseRepository.findAllVwExpenseByUserId(personel.getId(), personel.getCompanyId());
    }

    //Şirket yöneticisi gelen harcama istekleri için, sadece PENDING olanları görüyor
    public List<VwExpense> getAllExpenseRequestlist(String token){
        User companyManager = userService.getUserByToken(token);
        return expenseRepository.findAllVwExpenseByCompanyId(companyManager.getCompanyId());
    }


    //Personel yeni harcama isteği
    public Boolean createNewExpenseRequest(NewExpenseRequestDto dto){

        User personel = userService.getUserByToken(dto.token());
        Long companyManagerId = userService.findCompanyManagerIdByCompanyId(personel.getCompanyId());
        expenseRepository.save(Expense.builder()
                .userId(personel.getId())
                .approverId(companyManagerId)
                .receiptUrl(dto.receiptUrl())
                .description(dto.description())
                .status(EExpenseStatus.PENDING)
                .amount(dto.amount())
                .build());
        return true;
    }

    public Boolean addReceiptPhotoToExpense(String token,Long expenseId, MultipartFile file) throws IOException {
        Expense expense = checkCompany(token,expenseId);
        expense.setReceiptUrl(cloudinaryService.uploadFile(file));
        expenseRepository.save(expense);
        return true;
    }

    public Boolean updateExpense(UpdateExpenseRequestDto dto){
        User personel = userService.getUserByToken(dto.token());
        Expense expense = checkCompany(dto.token(),dto.expenseId());
        if(!personel.getId().equals(expense.getId())) throw new IKProjeException((ErrorType.UNAUTHORIZED));
        expense.setAmount(dto.amount());
        expense.setDescription(dto.description());
        expenseRepository.save(expense);
        return true;
    }

    //Şirket yöneticisi harcama isteği onaylama
    public Boolean approveExpenseRequest(String token, Long expenseId){
        Expense expense = checkCompany(token, expenseId);
        expense.setStatus(EExpenseStatus.APPROVED);
        UserDetails userDetails = userDetailsService.findByUserId(expense.getUserId());
        userDetails.setSalary(userDetails.getSalary() + expense.getAmount());
        expenseRepository.save(expense);
        userDetailsService.save(userDetails);
        return true;
    }

    //Şirket yöneticisi harcama isteği reddetme, reddedilince databaseden passive çekebiliriz belki.
    public Boolean rejectExpenseRequest(String token, Long expenseId){
        Expense expense = checkCompany(token, expenseId);
        expense.setStatus(EExpenseStatus.REJECTED);
        expenseRepository.save(expense);
        return true;
    }



    private Expense checkCompany(String token, Long expenseId) {
        User companyManager = userService.getUserByToken(token);
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(()->new IKProjeException(ErrorType.EXPENSE_NOT_FOUND));
        User user = userService.findById(expense.getUserId()).orElseThrow(()-> new IKProjeException(ErrorType.USER_NOTFOUND));
        if(!user.getCompanyId().equals(companyManager.getCompanyId())) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        return expense;
    }



}
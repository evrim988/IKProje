package org.example.ikproje.view;

import org.example.ikproje.entity.enums.EExpenseStatus;

public record VwExpense(

       Long id,
       String personalName,
       String approverName,
       EExpenseStatus status,
       Double amount,
       String description,
       String receiptUrl,
       Long expenseDate //harcama zamanı

) {


}

package org.example.ikproje.dto.request;

import org.example.ikproje.entity.enums.EExpenseStatus;

public record NewExpenseRequestDto(
        String token,
        Double amount,
        String description,
        String receiptUrl

) {
}

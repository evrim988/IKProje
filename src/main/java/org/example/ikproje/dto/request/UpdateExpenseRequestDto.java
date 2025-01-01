package org.example.ikproje.dto.request;

public record UpdateExpenseRequestDto(
        String token,
        Long expenseId,
        Double amount,
        String description
) {
}

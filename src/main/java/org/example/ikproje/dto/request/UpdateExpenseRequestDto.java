package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateExpenseRequestDto(
		@NotNull
        String token,
		@NotNull
        Long expenseId,
        Double amount,
        String description
) {
}
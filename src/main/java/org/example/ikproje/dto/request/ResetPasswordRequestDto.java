package org.example.ikproje.dto.request;

public record ResetPasswordRequestDto(
        String password,
        String rePassword
) {
}
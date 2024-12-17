package org.example.ikproje.dto.request;

public record ResetPasswordRequestDto(
        String token,
        String password,
        String rePassword
) {
}

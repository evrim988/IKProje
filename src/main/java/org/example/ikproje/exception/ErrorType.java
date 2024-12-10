package org.example.ikproje.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    INTERNAL_SERVER_ERROR(500,"Sunucuda beklenmeyen bir hata oldu. Lütfen tekrar deneyiniz",HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(400,"Girilen parametreler hatalıdır. Lütfen kontrol ederek tekrar deneyiniz.", HttpStatus.BAD_REQUEST),
    
    USER_NOTFOUND(5001,"Kullanıcı Bulunamadı!",HttpStatus.NOT_FOUND),
    INVALID_TOKEN(9001,"geçersiz token bilgisi",HttpStatus.BAD_REQUEST),
    ENCRYPTION_FAILED(7001,"Şifreleme başarısız.",HttpStatus.BAD_REQUEST),
    DECRYPTION_FAILED(7002,"Şifre çözümleme başarısız.",HttpStatus.BAD_REQUEST),
    INVALID_USERNAME_OR_PASSWORD(6002,"Kullanıcı adı ya da şifre hatalıdır",HttpStatus.BAD_REQUEST),
    PASSWORDS_NOT_MATCH(6003,"Girilen şifreler eşleşmiyor. Lütfen tekrar deneyiniz.",HttpStatus.BAD_REQUEST),
    MAIL_ALREADY_EXIST(6004,"Girilen mail adresi sistemde mevcut. Lütfen tekrar deneyiniz.",HttpStatus.BAD_REQUEST);


    int code;
    String message;
    HttpStatus httpStatus;
}
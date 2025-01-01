package org.example.ikproje.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    INTERNAL_SERVER_ERROR(500,"Sunucuda beklenmeyen bir hata oldu. Lütfen tekrar deneyiniz",HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(400,"Girilen parametreler hatalıdır. Lütfen kontrol ederek tekrar deneyiniz.", HttpStatus.BAD_REQUEST),
    PAGE_NOT_FOUND(404,"Sayfa bulunamadı", HttpStatus.NOT_FOUND),
    LEAVE_ERROR(6008,"İzin ile ilgili bir hata oluştu.", HttpStatus.NOT_FOUND),
    USER_NOTFOUND(5001,"Kullanıcı Bulunamadı!",HttpStatus.NOT_FOUND),
    COMPANY_NOTFOUND(5002,"Şirket Bulunamadı!",HttpStatus.NOT_FOUND),
    UNAUTHORIZED(5003,"Bu işlem için yetkiniz yok!",HttpStatus.FORBIDDEN),
    INVALID_TOKEN(9001,"Geçersiz token bilgisi",HttpStatus.BAD_REQUEST),
    EXP_TOKEN(9002,"Token'in süresi doldu!",HttpStatus.BAD_REQUEST),
    TOKEN_ALREADY_USED(9003,"Bu token zaten kullanıldı.",HttpStatus.BAD_REQUEST),
    ENCRYPTION_FAILED(7001,"Şifreleme başarısız.",HttpStatus.BAD_REQUEST),
    DECRYPTION_FAILED(7002,"Şifre çözümleme başarısız.",HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_ERROR(7003,"Dosya yüklemesi başarısız..",HttpStatus.BAD_REQUEST),
    INVALID_USERNAME_OR_PASSWORD(6002,"Kullanıcı adı ya da şifre hatalıdır",HttpStatus.BAD_REQUEST),
    PASSWORDS_NOT_MATCH(6003,"Girilen şifreler eşleşmiyor. Lütfen tekrar deneyiniz.",HttpStatus.BAD_REQUEST),
    MAIL_ALREADY_EXIST(6004,"Girilen mail adresi sistemde mevcut. Lütfen tekrar deneyiniz.",HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_APPROVED(6005, "Hesap zaten onaylanmıştır.", HttpStatus.BAD_REQUEST),
    USER_NOT_APPROVED(6006,"Site yöneticisi kaydınızı henüz onaylamamıştır veya kaydınız reddedilmiştir. Lütfen mail kutunuzu kontrol ediniz.",HttpStatus.BAD_REQUEST),
    MAIL_NOT_VERIFIED(6004,"Mail henüz onaylanmamış, lütfen mailinize gelen onay linkine tıklayınız.",
                      HttpStatus.BAD_REQUEST),
    MAIL_NOT_FOUND(6007,"Girilen mail ile kayıtlı kullanıcı bulunamadı.",HttpStatus.BAD_REQUEST),
    ADMIN_NOT_FOUND(9002,"Mail ya da şifre hatalı.",HttpStatus.BAD_REQUEST),
    NOT_ELIGIBLE_FOR_ANNUAL_LEAVE(6007,"İşe girişinizden itibaren geçen süre 1 seneden az olduğu için yıllık izin talebinde bulunamazsınız",HttpStatus.BAD_REQUEST),
    NOT_ELIGIBLE_FOR_MATERNITY_LEAVE(6008,"Doğum izni talebinde bulunamazsınız.",HttpStatus.BAD_REQUEST),
    ANNUAL_LEAVE_DAYS_EXCEEDED(6009,"İstediğiniz izin günü miktarı kalan yıllık izin günlerinizden daha fazladır",HttpStatus.BAD_REQUEST),
    SHIFT_NOT_FOUND(4001,"Vardiya bulunamadı!",HttpStatus.NOT_FOUND),
    SHIFT_TIME_ERROR(4002,"Vardiya bitiş günü başlangıç gününden önce olamaz!",HttpStatus.BAD_REQUEST),
    SHIFT_DATE_OVERLAP(4003,"Tanımlanan tarih aralığı başka bir aktif vardiya ile çakışıyor.",HttpStatus.BAD_REQUEST),
    BREAK_TIME_ERROR(4004,"Mola tanımlamak istenilen saatler vardiya saatlerinin dışındadır!",HttpStatus.BAD_REQUEST),
    BREAK_NOT_FOUND(4005,"Mola bulunamadı!",HttpStatus.NOT_FOUND),
    ASSET_NOT_FOUND(4101,"Zimmet bulunamadı",HttpStatus.NOT_FOUND),
    EXPENSE_NOT_FOUND(4201,"Harcama bulunamadı",HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND(4301,"Comment bulunamadı.",HttpStatus.NOT_FOUND),
    COMMENT_ALREADY_EXIST(4302,"Zaten önceden ana sayfa için yorum oluşturulmuş. Var olanı düzenlemeyi deneyin.",HttpStatus.BAD_REQUEST),;


    int code;
    String message;
    HttpStatus httpStatus;
}
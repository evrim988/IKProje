    package org.example.ikproje.view;


    /*
    Ziyaretçiler, kullanıcı yorumlarının detaylarında, ilgili yorumun tamamıyla birlikte, yorumu yazan şirket yöneticisinin
     bilgilerini ve şirket ile ilgili logo ve sayısal verileri görüntüleyebilmelidir.
     */

    import jakarta.persistence.Entity;
    import jakarta.persistence.Table;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDate;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public class VwCommentDetail {

            Long id;
            String companyName;
            String managerName;
            String managerPhoto;
            String companyLogo;
            String content;
            long totalPersonel;//toplam çalışan sayısı
            LocalDate managerBirthdate;
            String companyAddress; //şirket adresinden şehir dönüyor
            String companyPhone; //İletişim no gibi

            //başka detay olarak ne eklenebilir bilemedim



    }

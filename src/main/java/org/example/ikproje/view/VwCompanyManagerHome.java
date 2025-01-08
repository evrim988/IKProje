package org.example.ikproje.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VwCompanyManagerHome {
    Long personalOnLeaveCount; //izne ayrılan personel sayısı
    Long totalShiftCount;      //vardiya sayısı
    List<Object[]> departments; //departmanlar ve o departmanda çalışan insan sayıları
    List<Object[]> genderDistribution; //kadın Erkek çalışan sayıları, pie-chart için
    Long totalPersonelCount;
}

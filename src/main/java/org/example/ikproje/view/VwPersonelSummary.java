package org.example.ikproje.view;


import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserDepartmentType;
import org.example.ikproje.entity.enums.EUserWorkStatus;

import java.time.LocalDate;

//şirket yöneticisi için personel özet bilgilerinin olduğu liste
public record VwPersonelSummary(

        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        LocalDate hireDate,
        EUserDepartmentType departmentType,
        EState state,
        EUserWorkStatus userWorkStatus


) {
}

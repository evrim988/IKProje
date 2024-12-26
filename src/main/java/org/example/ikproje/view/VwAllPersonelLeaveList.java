package org.example.ikproje.view;

import org.example.ikproje.entity.enums.ELeaveStatus;
import org.example.ikproje.entity.enums.ELeaveType;

import java.time.LocalDate;

public record VwAllPersonelLeaveList(

        Long id,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        ELeaveType leaveType,
        ELeaveStatus leaveStatus,
        String managerName,
        String rejectResponse,
        LocalDate statusDate,
        String personelFirstName,
        String personelLastName

) {
}

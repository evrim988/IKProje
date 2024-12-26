package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ikproje.entity.enums.ELeaveStatus;
import org.example.ikproje.entity.enums.ELeaveType;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_leave")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long userId;
    Long companyId;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    @Enumerated(EnumType.STRING)
    ELeaveType leaveType;
    @Enumerated(EnumType.STRING)
    ELeaveStatus leaveStatus;
    String managerName;
    String rejectResponse;
    //hangi yönetici tarafından neden reddedildiği de field olarak olsun
    LocalDate statusDate;



}

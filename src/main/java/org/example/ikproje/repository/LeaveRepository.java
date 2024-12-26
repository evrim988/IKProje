package org.example.ikproje.repository;

import org.example.ikproje.entity.Leave;
import org.example.ikproje.entity.enums.ELeaveStatus;
import org.example.ikproje.entity.enums.ELeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave,Long> {

    List<Leave> findAllByLeaveStatusAndCompanyId(ELeaveStatus status,Long companyId);

    List<Leave> findAllByLeaveStatusAndUserId(ELeaveStatus status,Long userId);

    Optional<Leave> findTopByUserIdAndLeaveStatusAndLeaveTypeOrderByEndDateDesc(Long userId, ELeaveStatus leaveStatus, ELeaveType leaveType);

    Optional<Leave> findTopByUserIdOrderByEndDateDesc(Long userId);
}

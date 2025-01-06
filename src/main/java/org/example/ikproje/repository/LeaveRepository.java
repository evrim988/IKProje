package org.example.ikproje.repository;

import org.example.ikproje.entity.Leave;
import org.example.ikproje.entity.enums.ELeaveStatus;
import org.example.ikproje.entity.enums.ELeaveType;
import org.example.ikproje.view.VwAllPersonelLeaveList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave,Long> {



    List<Leave> findAllByUserId(Long userId);

    Optional<Leave> findTopByUserIdAndLeaveStatusAndLeaveTypeOrderByEndDateDesc(Long userId, ELeaveStatus leaveStatus, ELeaveType leaveType);

    Optional<Leave> findTopByUserIdOrderByEndDateDesc(Long userId);

    @Query("SELECT new org.example.ikproje.view.VwAllPersonelLeaveList(l.id,l.description,l.startDate,l.endDate,l.leaveType,l.leaveStatus,l.managerName,l.rejectResponse,l.statusDate,u.firstName,u.lastName) FROM Leave l JOIN User u ON u.id=l.userId WHERE l.companyId=?1 AND u.state=org.example.ikproje.entity.enums.EState.ACTIVE AND l.leaveStatus=org.example.ikproje.entity.enums.ELeaveStatus.PENDING")
    List<VwAllPersonelLeaveList> findAllVwPersonelLeaveList(Long companyId);
    
    Optional<Leave> findByUserId(Long userId);
    
    Optional<Leave> findTopByUserIdAndLeaveTypeOrderByEndDateDesc(Long id, ELeaveType eLeaveType);
    
}
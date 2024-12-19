package org.example.ikproje.repository;

import org.example.ikproje.entity.Leave;
import org.example.ikproje.entity.enums.ELeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave,Long> {

    List<Leave> findAllByLeaveStatusAndCompanyId(ELeaveStatus status,Long companyId);
}

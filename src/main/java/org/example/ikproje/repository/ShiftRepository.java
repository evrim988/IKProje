package org.example.ikproje.repository;

import org.example.ikproje.entity.Shift;
import org.example.ikproje.entity.enums.EState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
	List<Shift> getAllByCompanyIdAndState(Long companyId, EState state);
}
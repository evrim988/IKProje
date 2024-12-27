package org.example.ikproje.repository;

import org.example.ikproje.entity.Break;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BreakRepository extends JpaRepository<Break, Long> {
	List<Break> findByShiftId(Long shiftId);
	
}
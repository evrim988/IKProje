package org.example.ikproje.repository;

import org.example.ikproje.entity.Break;
import org.example.ikproje.entity.enums.EState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BreakRepository extends JpaRepository<Break, Long> {
	List<Break> findByShiftIdOrderById(Long shiftId);

	List<Break> findAllByStateOrderById(EState state);
}
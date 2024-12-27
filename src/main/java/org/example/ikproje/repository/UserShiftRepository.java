package org.example.ikproje.repository;

import org.example.ikproje.entity.UserShift;
import org.example.ikproje.entity.enums.EState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserShiftRepository extends JpaRepository<UserShift, Long> {
	
	List<UserShift> findByUserIdAndState(Long userId, EState state);
	
}
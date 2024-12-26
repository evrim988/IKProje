package org.example.ikproje.repository;

import org.example.ikproje.entity.LeaveDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails, Long> {
 Optional<LeaveDetails> findByUserId(Long userId);
}
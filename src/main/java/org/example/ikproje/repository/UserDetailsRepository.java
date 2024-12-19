package org.example.ikproje.repository;

import org.example.ikproje.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
   Optional<UserDetails> findByUserId(Long userId);
}
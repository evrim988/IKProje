package org.example.ikproje.repository;

import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
   Optional<UserDetails> findByUserId(Long userId);
   
   
}
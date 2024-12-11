package org.example.ikproje.repository;

import org.example.ikproje.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findOptionalByEmailAndPassword(String email, String password);
	
	Boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
}
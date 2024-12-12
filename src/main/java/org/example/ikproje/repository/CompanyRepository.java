package org.example.ikproje.repository;

import org.example.ikproje.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	Optional<Company> findOptionalByEmailAndPassword(String email, String password);
}
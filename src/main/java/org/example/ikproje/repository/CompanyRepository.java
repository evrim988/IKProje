package org.example.ikproje.repository;

import org.example.ikproje.entity.Company;
import org.example.ikproje.view.VwUnapprovedCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	Optional<Company> findOptionalByEmailAndPassword(String email, String password);
	
	@Query("SELECT NEW org.example.ikproje.view.VwUnapprovedCompany (c.id, c.name, c.phone, c.email) FROM Company c WHERE (c.isMailVerified=true AND c.isApproved=false) ")
	List<VwUnapprovedCompany> getAllUnapprovedCompanies();
}
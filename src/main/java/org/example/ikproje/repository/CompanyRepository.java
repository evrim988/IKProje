package org.example.ikproje.repository;

import org.example.ikproje.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	long count();
	
	@Query("SELECT c FROM Company c WHERE c.id IN :companyIds")
	List<Company> findCompaniesByIds(@Param("companyIds") List<Long> companyIds);

}
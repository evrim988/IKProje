package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Company;
import org.example.ikproje.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyRepository companyRepository;
	
	public void save(Company company) {
		companyRepository.save(company);
	}
}
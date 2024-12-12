package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Company;
import org.example.ikproje.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyRepository companyRepository;
	
	public void save(Company company) {
		companyRepository.save(company);
	}
	public Optional<Company> findById(Long id) {
		return companyRepository.findById(id);
	}
	
	public Optional<Company> findOptionalByEmailAndPassword(String email, String password) {
		return companyRepository.findOptionalByEmailAndPassword(email,password);
	}
}
package org.example.ikproje.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.UpdateCompanyLogoRequestDto;
import org.example.ikproje.entity.Company;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.CompanyRepository;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwUnapprovedCompany;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
	
	public List<VwUnapprovedCompany> getAllUnapprovedCompanies() {
		return companyRepository.getAllUnapprovedCompanies();
	}
}
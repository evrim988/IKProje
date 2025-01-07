package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Company;
import org.example.ikproje.repository.CompanyRepository;
import org.example.ikproje.view.VwUnapprovedAccounts;
import org.springframework.stereotype.Service;

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
	
	public long companyCount() {
		return companyRepository.count();
	}
	
	List<Company> findCompaniesByIds(List<Long> ids) {
		return companyRepository.findCompaniesByIds(ids);
	}
	
	public List<String> getRandomCompanyLogos(){
		List<Company> randomCompanies = companyRepository.findRandomCompanies();
		return randomCompanies.stream().map(Company::getLogo).filter(logo->logo!=null&&!logo.isEmpty()).toList();
	}
	
	
}
package org.example.ikproje.service;


import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.Membership;
import org.example.ikproje.repository.MembershipRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {
	private final MembershipRepository membershipRepository;
	private final CompanyService companyService;
	
	public void save(Membership membership) {
		membershipRepository.save(membership);
	}
	
	public List<Company> getCompaniesWithExpiringMemberships(LocalDate now , LocalDate oneMonthLater){
		List<Long> expiringCompanyIds = membershipRepository.findExpiringCompanyIds(now, oneMonthLater);
		return companyService.findCompaniesByIds(expiringCompanyIds);
	}
}
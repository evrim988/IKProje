package org.example.ikproje.service;


import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Membership;
import org.example.ikproje.repository.MembershipRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipService {
	private final MembershipRepository membershipRepository;
	
	public void save(Membership membership) {
		membershipRepository.save(membership);
	}
}
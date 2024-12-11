package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.VerificationToken;
import org.example.ikproje.repository.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {
	private final VerificationTokenRepository verificationTokenRepository;
	
	public void save(VerificationToken verificationToken) {
		verificationTokenRepository.save(verificationToken);
	}
	
	public Optional<VerificationToken> findByToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}
}
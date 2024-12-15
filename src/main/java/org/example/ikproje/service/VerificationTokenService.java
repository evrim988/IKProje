package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.VerificationToken;
import org.example.ikproje.repository.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
	
	/**
	 * Mail'e gidecek link için bir token oluşturdum.
	 * Bu token'in ilk 16 hanesi UUID'den gelen random rakamlar, harfler ve özel karakterlerden oluşuyor.
	 * İlk 16 haneden sonraki haneler ise sistemin şuanki zamanına 1 dakika ekleyecek bir epoch time içeriyor.
	 * verifyAccount methodunu kullanırken eğer token üretildikten sonra 1 dakikadan fazla bir zaman geçtiyse,
	 * token'in süresi doldu diye bir hata veriyor.
	 * @param companyId
	 * @return
	 */
	public String generateVerificationToken(Long companyId){
		StringBuilder verificationTokenSB = new StringBuilder();
		String token = UUID.randomUUID().toString();
		verificationTokenSB.append(token.substring(0,16));
		verificationTokenSB.append(System.currentTimeMillis()+(1000*60));
		VerificationToken verificationToken = new VerificationToken(verificationTokenSB.toString(),companyId);
		verificationTokenRepository.save(verificationToken);
		return verificationTokenSB.toString();
	}
}
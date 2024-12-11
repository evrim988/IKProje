package org.example.ikproje.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.dto.request.UserRegisterRequestDto;
import org.example.ikproje.entity.*;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
//import org.example.ikproje.mapper.AddressMapper;
import org.example.ikproje.mapper.AddressMapper;
import org.example.ikproje.mapper.CompanyMapper;
import org.example.ikproje.mapper.UserDetailsMapper;
import org.example.ikproje.mapper.UserMapper;
import org.example.ikproje.repository.UserRepository;
import org.example.ikproje.utility.EncryptionManager;
import org.example.ikproje.utility.JwtManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final CompanyService companyService;
	private final UserDetailsService userDetailsService;
	private final AddressService addressService;
	private final JwtManager jwtManager;
	private final EmailService emailService;
	private final VerificationTokenService verificationTokenService;
	
	@Transactional
	public void register( UserRegisterRequestDto dto) {
		if (userRepository.existsByEmail(dto.email())){
			throw new IKProjeException(ErrorType.MAIL_ALREADY_EXIST);
		}
		User user = UserMapper.INSTANCE.fromRegisterDto(dto);
		user.setUserRole(EUserRole.COMPANY_MANAGER);
		String encryptedPassword = getEncryptedPassword(dto.password());
		user.setPassword(encryptedPassword);
		user.setState(EState.PASSIVE);
		user.setAvatarUrl(dto.avatarUrl());
		Address userAddress = AddressMapper.INSTANCE.toUserAddress(dto);
		Address companyAddress = AddressMapper.INSTANCE.toCompanyAddress(dto);
		addressService.saveAll(Arrays.asList(userAddress, companyAddress));
		UserDetails userDetails = UserDetailsMapper.INSTANCE.fromRegisterDto(dto);
		userDetails.setAddressId(userAddress.getId());
		Company company = CompanyMapper.INSTANCE.fromRegisterDto(dto);
		company.setAddressId(companyAddress.getId());
		companyService.save(company);
		user.setCompanyId(company.getId());
		user.setState(EState.ACTIVE);
		userRepository.save(user);
		userDetails.setUserId(user.getId());
		userDetailsService.save(userDetails);
		//Mail gönderirken alıcı olarak dto'dan gelen (şirket yöneticisi) mail adresi ve oluşturduğum verification
		// token'i giriyorum
		emailService.sendEmail(dto.email(),generateVerificationToken(user.getId()));
	}
	
	/**
	 * Mail'e gidecek link için bir token oluşturdum.
	 * Bu token'in ilk 16 hanesi UUID'den gelen random rakamlar, harfler ve özel karakterlerden oluşuyor.
	 * İlk 16 haneden sonraki haneler ise sistemin şuanki zamanına 1 dakika ekleyecek bir epoch time içeriyor.
	 * verifyAccount methodunu kullanırken eğer token üretildikten sonra 1 dakikadan fazla bir zaman geçtiyse,
	 * token'in süresi doldu diye bir hata veriyor.
	 * @param userId
	 * @return
	 */
	public String generateVerificationToken(Long userId){
		StringBuilder verificationTokenSB = new StringBuilder();
		String token = UUID.randomUUID().toString();
		verificationTokenSB.append(token.substring(0,16));
		verificationTokenSB.append(System.currentTimeMillis()+(1000*60));
		VerificationToken verificationToken = new VerificationToken(verificationTokenSB.toString(),userId);
		verificationTokenService.save(verificationToken);
		return verificationTokenSB.toString();
	}
	
	/**
	 * Bu method'a parametre olarak gelen token, veritabanında var mı diye kontrol ediliyor.
	 * Daha sonra token'in süresi dolmuş mu diye kontrol ediliyor.
	 * Daha sonra token'in içerisinden userId alınıp, user'ın varlığı kontrol ediliyor.
	 * Eğer tüm şartlar sağlanıyorsa mail onayı gerçekleşiyor ve token'in state'i passive oluyor.
	 * @param verificationToken
	 */
	public void verifyAccount(String verificationToken){
		Optional<VerificationToken> optVerificationToken = verificationTokenService.findByToken(verificationToken);
		if (optVerificationToken.isEmpty()){
			throw new IKProjeException(ErrorType.INVALID_TOKEN);
		}
		VerificationToken verToken = optVerificationToken.get();
		if (verToken.getState()==EState.PASSIVE){
			throw new IKProjeException(ErrorType.TOKEN_ALREADY_USED);
		}
		long expDate = Long.parseLong(verificationToken.substring(16));
		if (expDate<System.currentTimeMillis()){
			verToken.setState(EState.PASSIVE);
			verificationTokenService.save(verToken);
			throw new IKProjeException(ErrorType.EXP_TOKEN);
		}
		Long userId = verToken.getUserId();
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isEmpty()){
			throw new IKProjeException(ErrorType.USER_NOTFOUND);
		}
		User user = optUser.get();
		user.setIsMailVerified(true);
		verToken.setState(EState.PASSIVE);
		userRepository.save(user);
		verificationTokenService.save(verToken);
	}
	

	
	public String login(LoginRequestDto dto) {
		String encryptedPassword = getEncryptedPassword(dto.password());
		System.out.println(encryptedPassword);
		Optional<User> optUser =
				userRepository.findOptionalByEmailAndPassword(dto.email(),encryptedPassword );
		System.out.println(optUser.isPresent());
		if (optUser.isEmpty()){
			throw new IKProjeException(ErrorType.INVALID_USERNAME_OR_PASSWORD);
		}
		return jwtManager.createUserToken(optUser.get().getId());
	}
	
	private static String getEncryptedPassword(String password) {
		String encryptedPassword = "";
		try {
			encryptedPassword = EncryptionManager.encrypt(password);
		}
		catch (Exception e) {
			throw new IKProjeException(ErrorType.ENCRYPTION_FAILED);
		}
		return encryptedPassword;
	}
}
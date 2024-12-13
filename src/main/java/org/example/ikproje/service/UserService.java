package org.example.ikproje.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.dto.request.RegisterRequestDto;
import org.example.ikproje.dto.response.UserProfileResonseDto;
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
	public void register( RegisterRequestDto dto) {
		if (userRepository.existsByEmail(dto.companyEmail())){
			throw new IKProjeException(ErrorType.MAIL_ALREADY_EXIST);
		}
		User user = UserMapper.INSTANCE.fromRegisterDto(dto);
		user.setUserRole(EUserRole.COMPANY_MANAGER);
		String encryptedPassword = getEncryptedPassword(dto.companyPassword());
		user.setEmail(dto.companyEmail());
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
		company.setPassword(encryptedPassword);
		companyService.save(company);
		user.setCompanyId(company.getId());
		user.setState(EState.ACTIVE);
		userRepository.save(user);
		userDetails.setUserId(user.getId());
		userDetailsService.save(userDetails);
		//Mail gönderirken alıcı olarak dto'dan gelen (şirket yöneticisi) mail adresi ve oluşturduğum verification
		// token'i giriyorum
		emailService.sendEmail(dto.companyEmail(),generateVerificationToken(user.getId()));
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
		verificationTokenService.save(verificationToken);
		return verificationTokenSB.toString();
	}
	
	/**
	 * Bu method'a parametre olarak gelen token, veritabanında var mı diye kontrol ediliyor.
	 * Daha sonra token'in süresi dolmuş mu diye kontrol ediliyor.
	 * Daha sonra token'in içerisinden companyId alınıp, company'nin varlığı kontrol ediliyor.
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
		Long companyId = verToken.getCompanyId();
		Optional<Company> optCompany = companyService.findById(companyId);
		if (optCompany.isEmpty()){
			throw new IKProjeException(ErrorType.COMPANY_NOTFOUND);
		}
		Company company = optCompany.get();
		company.setIsMailVerified(true);
		verToken.setState(EState.PASSIVE);
		companyService.save(company);
		verificationTokenService.save(verToken);
	}
	

	
	public String login(LoginRequestDto dto) {
		String encryptedPassword = getEncryptedPassword(dto.password());
		Optional<Company> optCompany =
				companyService.findOptionalByEmailAndPassword(dto.email(), encryptedPassword);
		if (optCompany.isEmpty()){
			throw new IKProjeException(ErrorType.INVALID_USERNAME_OR_PASSWORD);
		}
		Company company = optCompany.get();
		if(!company.getIsMailVerified()){
			emailService.sendEmail(dto.email(),generateVerificationToken(company.getId()));
			throw new IKProjeException(ErrorType.MAIL_NOT_VERIFIED);
		}
		return jwtManager.createUserToken(company.getId());
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

	public UserProfileResonseDto getProfile(String token) {
		Optional<Long> optionalUserId = jwtManager.validateToken(token);
		if(optionalUserId.isEmpty()){
			throw new IKProjeException(ErrorType.INVALID_TOKEN);
		}
		Optional<User> optionalUser = userRepository.findById(optionalUserId.get());
		if (optionalUser.isEmpty()){
			throw new IKProjeException(ErrorType.USER_NOTFOUND);
		}
		UserProfileResonseDto dto = UserProfileResonseDto.builder()
				.firstName(optionalUser.get().getFirstName())
				.lastName(optionalUser.get().getLastName())
				.avatarUrl(optionalUser.get().getAvatarUrl())
				.build();
		return dto;
	}
}
package org.example.ikproje.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.dto.request.UserRegisterRequestDto;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.mapper.CompanyMapper;
import org.example.ikproje.mapper.UserMapper;
import org.example.ikproje.repository.UserRepository;
import org.example.ikproje.utility.EncryptionManager;
import org.example.ikproje.utility.JwtManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final CompanyService companyService;
	private final JwtManager jwtManager;
	
	public void register( UserRegisterRequestDto dto) {
		User user = UserMapper.INSTANCE.fromRegisterDto(dto);
		user.setUserRole(EUserRole.COMPANY_MANAGER);
		String encryptedPassword = getEncryptedPassword(dto.password());
		user.setPassword(encryptedPassword);
		Company company= CompanyMapper.INSTANCE.fromRegisterDto(dto);
		companyService.save(company);
		user.setCompanyId(company.getId());
		userRepository.save(user);
	}
	
	public String login(LoginRequestDto dto) {
		String encryptedPassword = getEncryptedPassword(dto.password());
		System.out.println(encryptedPassword);
		Optional<User> optUser =
				userRepository.findOptionalByUsernameAndPassword(dto.username(),encryptedPassword );
		System.out.println(optUser.isPresent());
		if (optUser.isEmpty()){
			throw new IKProjeException(ErrorType.INVALID_USERNAME_OR_PASSWORD);
		}
		return jwtManager.createToken(optUser.get().getId());
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
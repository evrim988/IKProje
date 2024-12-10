package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.dto.request.UpdateInfoRequestDto;
import org.example.ikproje.dto.request.UserRegisterRequestDto;
import org.example.ikproje.entity.Address;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserDetails;
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

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final CompanyService companyService;
	private final UserDetailsService userDetailsService;
	private final AddressService addressService;
	private final JwtManager jwtManager;
	
	public void register( UserRegisterRequestDto dto) {
		if (userRepository.existsByEmail(dto.email())){
			throw new IKProjeException(ErrorType.MAIL_ALREADY_EXIST);
		}
		User user = UserMapper.INSTANCE.fromRegisterDto(dto);
		user.setUserRole(EUserRole.COMPANY_MANAGER);
		String encryptedPassword = getEncryptedPassword(dto.password());
		user.setPassword(encryptedPassword);
		user.setState(EState.PASSIVE);
		userRepository.save(user);
	}
	
	public boolean update(UpdateInfoRequestDto dto){
		Optional<User> optUser = userRepository.findById(dto.userId());
		if (optUser.isEmpty()){
			throw new IKProjeException(ErrorType.USER_NOTFOUND);
		}
		try{
			User user = optUser.get();
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
			userDetails.setUserId(user.getId());
			userDetailsService.save(userDetails);
			user.setState(EState.ACTIVE);
			userRepository.save(user);
			return true;
		}catch (Exception e){
			return false;
		}
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
package org.example.ikproje.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.dto.request.RegisterRequestDto;
import org.example.ikproje.dto.request.UpdateCompanyLogoRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.dto.response.UserProfileResponseDto;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.service.UserService;
import org.example.ikproje.view.VwPersonel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.ikproje.constant.RestApis.*;
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
	private final UserService userService;
	
	@PostMapping(value = REGISTER)
	public ResponseEntity<BaseResponse<Boolean>> register(
			@RequestBody @Valid RegisterRequestDto dto) {
		
		if (!dto.companyPassword().equals(dto.companyRePassword())){
			throw new IKProjeException(ErrorType.PASSWORDS_NOT_MATCH);
		}
		
		userService.register(dto);
		
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(true)
		                                     .message("Üyelik başarıyla oluşturuldu.")
		                                     .success(true)
		                                     .build());
	}
	
	@PostMapping(LOGIN)
	public ResponseEntity<BaseResponse<String>> login(@RequestBody @Valid LoginRequestDto dto) {
		return ResponseEntity.ok(BaseResponse.<String>builder()
				                         .code(200)
				                         .data(userService.login(dto))
				                         .success(true)
				                         .message("Giriş başarılı.")
		                                     .build());
	}
	
	@GetMapping(VERIFY_ACCOUNT)
	public ResponseEntity<BaseResponse<Boolean>> verifyAccount(@RequestParam("token") String token){
		userService.verifyAccount(token);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .message("Mail başarıyla onaylandı.")
				                         .data(true)
				                         .success(true)
		                                 .build());
	}

	@GetMapping(GETPROFILE)
	public ResponseEntity<BaseResponse<UserProfileResponseDto>> getProfile(String token){
		return ResponseEntity.ok(BaseResponse.<UserProfileResponseDto>builder()
						.code(200)
						.message("Profil bilgisi başarıyla getirildi.")
						.data(userService.getProfile(token))
						.success(true)
				.build());
	}
	
	@PostMapping(value = UPDATE_COMPANY_LOGO,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseResponse<Boolean>> addLogoToCompany(String token,
	                                                              @ModelAttribute @Valid UpdateCompanyLogoRequestDto dto, MultipartFile file)
			throws IOException {
		userService.addLogoToCompany(token,dto,file);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .data(true)
				                         .success(true)
				                         .message("Şirket logosu eklendi.")
		                                     .build());
	}

	@GetMapping("get-personel-profile")
	public ResponseEntity<BaseResponse<VwPersonel>> getPersonelProfile(@RequestParam String token){
		return ResponseEntity.ok(BaseResponse.<VwPersonel>builder()
						.code(200)
						.data(userService.getPersonelProfile(token))
						.success(true)
						.message("Personel profili getirildi.")
				.build());
	}



}
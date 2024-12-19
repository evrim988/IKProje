package org.example.ikproje.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.*;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.service.UserService;
import org.example.ikproje.view.VwCompanyManager;
import org.example.ikproje.view.VwPersonel;
import org.example.ikproje.view.VwPersonelSummary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
		
		if (!dto.password().equals(dto.rePassword())){
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

	
	@PostMapping(value = UPDATE_COMPANY_LOGO,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseResponse<Boolean>> addLogoToCompany(@RequestParam String token,
																  @RequestParam MultipartFile file)
			throws IOException {
		userService.addLogoToCompany(token,file);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .data(true)
				                         .success(true)
				                         .message("Şirket logosu eklendi.")
		                                     .build());
	}
	
	@PostMapping(value = UPDATE_USER_AVATAR,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseResponse<Boolean>> addAvatarToUser(@RequestParam String token,
	                                                             @RequestParam MultipartFile file)
			throws IOException {
		userService.addAvatarToUser(token,file);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(true)
		                                     .success(true)
		                                     .message("User avatarı eklendi.")
		                                     .build());
	}

	@GetMapping("/get-personel-profile")
	public ResponseEntity<BaseResponse<VwPersonel>> getPersonelProfile(@RequestParam String token){
		return ResponseEntity.ok(BaseResponse.<VwPersonel>builder()
						.code(200)
						.data(userService.getPersonelProfile(token))
						.success(true)
						.message("Personel profili getirildi.")
				.build());
	}

	@GetMapping("/get-company-manager-profile")
	public ResponseEntity<BaseResponse<VwCompanyManager>> getCompanyManagerProfile(@RequestParam String token){
		return ResponseEntity.ok(BaseResponse.<VwCompanyManager>builder()
				.code(200)
				.data(userService.getCompanyManagerProfile(token))
				.success(true)
				.message("Şirket yöneticisi profili getirildi.")
				.build());
	}

	@PostMapping(FORGOT_PASSWORD)
	public ResponseEntity<BaseResponse<Boolean>> forgotPassword(@RequestParam String email){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
						.data(userService.forgotPassword(email))
						.code(200)
						.message("Şifre sıfırlama linki kullanıcı mailine gönderildi.")
						.success(true)
				.build());
	}

	@PostMapping(RESET_PASSWORD)
	public ResponseEntity<BaseResponse<Boolean>> resetPassword(@RequestBody ResetPasswordRequestDto dto){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				.data(userService.resetPassword(dto))
				.code(200)
				.message("Şifre başarı ile değiştirildi.")
				.success(true)
				.build());
	}

	@PostMapping(ADD_PERSONEL)
	public ResponseEntity<BaseResponse<Boolean>> addNewPersonel(@RequestBody @Valid CreateNewPersonelRequestDto dto){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
						.success(userService.addNewPersonel(dto))
						.message("Yeni personel eklendi.")
						.code(200)
				.build());
	}

	@PostMapping(UPDATE_PERSONEL_STATE)
	public ResponseEntity<BaseResponse<Boolean>> updatePersonelState(@RequestBody @Valid UpdatePersonelStateRequestDto dto){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				.success(userService.updatePersonalState(dto))
				.message("Personel aktiflik durumu güncellendi.")
				.code(200)
				.build());
	}

	//Personel özet bilgilerini liste halinde getiren endpoint, şirket yöneticisi için
	@GetMapping(GET_PERSONEL_LIST)
	public ResponseEntity<BaseResponse<List<VwPersonelSummary>>> getPersonelList(@RequestParam String token){
		return ResponseEntity.ok(BaseResponse.<List<VwPersonelSummary>>builder()
				.data(userService.getPersonelList(token))
				.success(true)
				.message("Şirket personel listesi getirildi.")
				.code(200)
				.build());
	}

	@PutMapping("/update-personel-profile")
	public ResponseEntity<BaseResponse<Boolean>> updatePersonelProfile(@RequestBody @Valid UpdatePersonelProfileRequestDto dto){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
						.success(userService.updatePersonelProfile(dto))
						.message("Kullanıcı profili güncellendi.")
						.code(200)
				.build());
	}
	@PutMapping("/update-company-manager-profile")
	public ResponseEntity<BaseResponse<Boolean>> updateCompanyManagerProfile(@RequestBody @Valid UpdateCompanyManagerProfileRequestDto dto){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				.success(userService.updateCompanyManagerProfile(dto))
				.message("Yönetici profili güncellendi.")
				.code(200)
				.build());
	}




}
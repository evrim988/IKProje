package org.example.ikproje.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.dto.request.UserRegisterRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.ikproje.constant.RestApis.*;
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
	private final UserService userService;
	
	@PostMapping(REGISTER)
	public ResponseEntity<BaseResponse<Boolean>> register(@RequestBody @Valid UserRegisterRequestDto dto){
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

}
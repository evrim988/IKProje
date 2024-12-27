package org.example.ikproje.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.AssignShiftToUserRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.UserShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.ikproje.constant.RestApis.*;


@RestController
@RequestMapping(USERSHIFT)
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserShiftController {
	private final UserShiftService userShiftService;
	
	@PostMapping(ASSIGN_SHIFT_TO_USER)
	public ResponseEntity<BaseResponse<Boolean>> assignShiftToUser(@RequestBody @Valid AssignShiftToUserRequestDto dto){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .message("Personele vardiya ataması başarılı.")
				                         .success(true)
				                         .data(userShiftService.assignShiftToUser(dto))
		                                     .build());
	}
}
package org.example.ikproje.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.CreateNewBreakRequestDto;
import org.example.ikproje.dto.request.UpdateBreakRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.dto.response.BreakResponseDto;
import org.example.ikproje.entity.Break;
import org.example.ikproje.service.BreakService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;


@RequiredArgsConstructor
@RequestMapping(BREAK)
@RestController
@CrossOrigin("*")
public class BreakController {
	private final BreakService breakService;
	
	@PostMapping(ADD_NEW_BREAK)
	public ResponseEntity<BaseResponse<Boolean>> createNewBreak(@RequestBody @Valid CreateNewBreakRequestDto dto){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .message("Mola oluşturuldu")
				                         .success(true)
				                         .data(breakService.createNewBreak(dto))
		                                     .build());
	}
	@PutMapping(UPDATE_BREAK)
	public ResponseEntity<BaseResponse<Boolean>> updateBreak(@RequestBody @Valid UpdateBreakRequestDto dto){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .message("Mola güncellendi")
				                         .success(true)
				                         .data(breakService.updateBreak(dto))
		                                     .build());
	}
	
	@PostMapping(DELETE_BREAK)
	public ResponseEntity<BaseResponse<Boolean>> deleteBreak(@RequestParam String token, @RequestParam Long breakId){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .success(true)
				                         .message("Mola başarıyla silindi")
				                         .data(breakService.deleteBreak(token,breakId))
		                                     .build());
	}

	//şirkete ait mola listesini getirir.
	@GetMapping(GET_ALL_BREAK)
	@PreAuthorize("hasAnyAuthority('COMPANY_MANAGER','EMPLOYEE')")
	public ResponseEntity<BaseResponse<List<BreakResponseDto>>> getAllBreak(@RequestParam String token){
		return ResponseEntity.ok(BaseResponse.<List<BreakResponseDto>>builder()
						.code(200)
						.success(true)
						.data(breakService.getAllBreak(token))
				.build());
	}
	
	@GetMapping(GET_BREAKS_BY_SHIFT_ID)
	@PreAuthorize("hasAnyAuthority('COMPANY_MANAGER','EMPLOYEE')")
	public ResponseEntity<BaseResponse<List<Break>>> getBreaksByShiftId(@RequestParam String token, @RequestParam Long shiftId){
		return ResponseEntity.ok(BaseResponse.<List<Break>>builder()
				                         .code(200)
				                         .message("Vardiya'ya ait tüm molalar getirildi.")
				                         .success(true)
				                         .data(breakService.getBreaksByShiftId(token,shiftId))
		                                     .build());
	}
}
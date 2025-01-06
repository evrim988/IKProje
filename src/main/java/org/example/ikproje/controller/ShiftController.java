package org.example.ikproje.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.CreateNewShiftRequestDto;
import org.example.ikproje.dto.request.UpdateShiftRequestDto;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.entity.Shift;
import org.example.ikproje.service.ShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RequiredArgsConstructor
@RequestMapping(SHIFT)
@RestController
@CrossOrigin("*")
public class ShiftController {
	private final ShiftService shiftService;
	
	@PostMapping(NEW_SHIFT_REQUEST)
	@PreAuthorize("hasAnyAuthority('COMPANY_MANAGER','EMPLOYEE')")
	public ResponseEntity<BaseResponse<Boolean>> createNewShift(@RequestBody @Valid CreateNewShiftRequestDto dto){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .message("Vardiya başarıyla eklendi.")
				                         .success(true)
				                         .data(shiftService.createNewShift(dto))
		                                 .build());
	}
	
	@PutMapping(UPDATE_SHIFT)
	@PreAuthorize("hasAnyAuthority('COMPANY_MANAGER','EMPLOYEE')")
	public ResponseEntity<BaseResponse<Boolean>> updateShift(@RequestBody @Valid UpdateShiftRequestDto dto){
		
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
				                         .code(200)
				                         .message("Vardiya başarıyla güncellendi.")
				                         .success(true)
				                         .data(shiftService.updateShift(dto))
		                                     .build());
	}
	
	@PutMapping(DELETE_SHIFT)
	@PreAuthorize("hasAnyAuthority('COMPANY_MANAGER','EMPLOYEE')")
	public ResponseEntity<BaseResponse<Boolean>> deleteShift(@RequestParam String token, @RequestParam Long shiftId){
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .message("Vardiya başarıyla silindi.")
		                                     .success(true)
		                                     .data(shiftService.deleteShift(token,shiftId))
		                                     .build());
	}
	
	@GetMapping(ALL_SHIFTS_BY_COMPANY)
	public ResponseEntity<BaseResponse<List<Shift>>> getAllShiftsByCompanyId(@RequestParam String token){
		return ResponseEntity.ok(BaseResponse.<List<Shift>>builder()
				                         .code(200)
				                         .message("Tüm vardiyalar getirildi.")
				                         .success(true)
				                         .data(shiftService.getAllShiftsByCompanyId(token))
		                                     .build());
	}
}
package org.example.ikproje.controller;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequestMapping(COMPANY)
@RequiredArgsConstructor
@CrossOrigin("*")
public class CompanyController {
	private final CompanyService companyService;
	
	@GetMapping(COMPANY_COUNT)
	public ResponseEntity<BaseResponse<Long>> companyCount(){
		return ResponseEntity.ok(BaseResponse.<Long>builder()
				                         .code(200)
				                         .success(true)
				                         .data(companyService.companyCount())
				                         .message("Şirket sayısı getirildi.")
		                                 .build());
	}
	
	@GetMapping(RANDOM_COMPANY_LOGOS)
	public ResponseEntity<BaseResponse<List<String>>> companyLogos(){
		return ResponseEntity.ok(BaseResponse.<List<String>>builder()
				                         .code(200)
				                         .success(true)
				                         .message("Şirket logoları getirildi")
				                         .data(companyService.getRandomCompanyLogos())
		                                     .build());
	}
	
}
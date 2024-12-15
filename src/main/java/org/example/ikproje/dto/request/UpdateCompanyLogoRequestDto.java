package org.example.ikproje.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateCompanyLogoRequestDto(
		
		Long companyId
) {
}
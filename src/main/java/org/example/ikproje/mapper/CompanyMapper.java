package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.RegisterRequestDto;
import org.example.ikproje.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {
	CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);
	
	@Mapping(target = "name", source = "companyName")
	@Mapping(target = "phone", source = "companyPhone")
	@Mapping(target = "email", source = "companyEmail")
	@Mapping(target = "password", source = "companyPassword")
	@Mapping(target = "logo", source = "companyLogo")
	@Mapping(target = "foundationDate", source = "companyFoundationDate")
	@Mapping(target = "industry", source = "companyIndustry")
	Company fromRegisterDto(RegisterRequestDto dto);
}
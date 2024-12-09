package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.UserRegisterRequestDto;
import org.example.ikproje.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {
	CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);
	
	@Mapping(target = "name", source = "companyName")
	@Mapping(target = "address", source = "companyAddress")
	@Mapping(target = "phone", source = "companyPhone")
	@Mapping(target = "email", source = "companyEmail")
	@Mapping(target = "logo", source = "companyLogo")
	Company fromRegisterDto(UserRegisterRequestDto dto);
}
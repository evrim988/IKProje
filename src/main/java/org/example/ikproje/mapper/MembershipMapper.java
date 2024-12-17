package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.RegisterRequestDto;
import org.example.ikproje.entity.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MembershipMapper {
	MembershipMapper INSTANCE = Mappers.getMapper(MembershipMapper.class);
	
	
	Membership fromRegisterDto(RegisterRequestDto dto);
}
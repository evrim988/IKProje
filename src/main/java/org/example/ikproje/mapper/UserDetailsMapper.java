package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.UpdateInfoRequestDto;
import org.example.ikproje.dto.request.UserRegisterRequestDto;
import org.example.ikproje.entity.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDetailsMapper {
	UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);
	
	UserDetails fromRegisterDto(UpdateInfoRequestDto dto);
}
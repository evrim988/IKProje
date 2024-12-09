package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.UserRegisterRequestDto;
import org.example.ikproje.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	User fromRegisterDto(UserRegisterRequestDto dto);
}
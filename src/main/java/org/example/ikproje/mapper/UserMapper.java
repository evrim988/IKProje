package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.RegisterRequestDto;
import org.example.ikproje.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	User fromRegisterDto(RegisterRequestDto dto);
}
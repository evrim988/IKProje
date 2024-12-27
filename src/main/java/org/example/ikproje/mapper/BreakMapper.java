package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.CreateNewBreakRequestDto;
import org.example.ikproje.entity.Break;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BreakMapper {
	BreakMapper INSTANCE = Mappers.getMapper(BreakMapper.class);
	
	Break fromCreateNewBreakDto(CreateNewBreakRequestDto dto);
}
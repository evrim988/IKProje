package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.CreateNewShiftRequestDto;
import org.example.ikproje.entity.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShiftMapper {
	ShiftMapper INSTANCE = Mappers.getMapper(ShiftMapper.class);
	
	Shift fromCreateNewShiftDto(CreateNewShiftRequestDto dto);
}
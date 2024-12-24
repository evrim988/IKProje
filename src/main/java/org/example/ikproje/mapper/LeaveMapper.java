package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.NewLeaveRequestDto;
import org.example.ikproje.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaveMapper {
	LeaveMapper INSTANCE = Mappers.getMapper(LeaveMapper.class);
	
	Leave fromNewLeaveDto(NewLeaveRequestDto dto);
}
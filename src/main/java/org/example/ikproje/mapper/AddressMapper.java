package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.RegisterRequestDto;
import org.example.ikproje.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
	
	@Mapping(target = "region", source = "region")
	@Mapping(target = "city", source = "city")
	@Mapping(target = "district", source = "district")
	@Mapping(target = "neighbourhood", source = "neighbourhood")
	@Mapping(target = "street", source = "street")
	@Mapping(target = "postalCode", source = "postalCode")
	@Mapping(target = "aptNumber", source = "aptNumber")
	Address toUserAddress(RegisterRequestDto dto);
	
	
	@Mapping(target = "region", source = "companyRegion")
	@Mapping(target = "city", source = "companyCity")
	@Mapping(target = "district", source = "companyDistrict")
	@Mapping(target = "neighbourhood", source = "companyNeighbourhood")
	@Mapping(target = "street", source = "companyStreet")
	@Mapping(target = "postalCode", source = "companyPostalCode")
	@Mapping(target = "aptNumber", source = "companyAptNumber")
	Address toCompanyAddress(RegisterRequestDto dto);

}
package org.example.ikproje.mapper;

import org.example.ikproje.dto.request.UpdateInfoRequestDto;
import org.example.ikproje.dto.request.UserRegisterRequestDto;
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
	@Mapping(target = "neighborhood", source = "neighborhood")
	@Mapping(target = "street", source = "street")
	@Mapping(target = "postalCode", source = "postalCode")
	@Mapping(target = "aptNumber", source = "aptNumber")
	Address toUserAddress(UpdateInfoRequestDto dto);
	
	
	@Mapping(target = "region", source = "companyRegion")
	@Mapping(target = "city", source = "companyCity")
	@Mapping(target = "district", source = "companyDistrict")
	@Mapping(target = "neighborhood", source = "companyNeighborhood")
	@Mapping(target = "street", source = "companyStreet")
	@Mapping(target = "postalCode", source = "companyPostalCode")
	@Mapping(target = "aptNumber", source = "companyAptNumber")
	Address toCompanyAddress(UpdateInfoRequestDto dto);

}
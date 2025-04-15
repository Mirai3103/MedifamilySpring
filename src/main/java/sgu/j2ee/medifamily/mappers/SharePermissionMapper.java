package sgu.j2ee.medifamily.mappers;

import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.SharePermissionDto;
import sgu.j2ee.medifamily.entities.SharePermission;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Primary

public interface SharePermissionMapper {
	SharePermission toEntity(SharePermissionDto sharePermissionDto);

	SharePermissionDto toDto(SharePermission sharePermission);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	SharePermission partialUpdate(SharePermissionDto sharePermissionDto,
			@MappingTarget SharePermission sharePermission);
}
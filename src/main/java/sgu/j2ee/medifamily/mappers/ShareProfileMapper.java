package sgu.j2ee.medifamily.mappers;

import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.ShareProfileDto;
import sgu.j2ee.medifamily.entities.ShareProfile;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
		IFamilyMapper.class, IFamilyMemberMapper.class, SharePermissionMapper.class })
@Primary
public interface ShareProfileMapper {
	ShareProfile toEntity(ShareProfileDto shareProfileDto);

	ShareProfileDto toDto(ShareProfile shareProfile);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	ShareProfile partialUpdate(ShareProfileDto shareProfileDto, @MappingTarget ShareProfile shareProfile);
}
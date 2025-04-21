package sgu.j2ee.medifamily.mappers;

import java.util.List;

import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.ProfileDocumentDto;
import sgu.j2ee.medifamily.entities.ProfileDocument;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Primary
public interface ProfileDocumentMapper {
	ProfileDocument toEntity(ProfileDocumentDto profileDocumentDto);

	ProfileDocumentDto toDto(ProfileDocument profileDocument);

	List<ProfileDocumentDto> toDto(List<ProfileDocument> profileDocument);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	ProfileDocument partialUpdate(ProfileDocumentDto profileDocumentDto,
			@MappingTarget ProfileDocument profileDocument);
}
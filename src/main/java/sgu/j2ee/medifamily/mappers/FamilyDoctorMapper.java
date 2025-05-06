package sgu.j2ee.medifamily.mappers;

import java.util.List;

import org.mapstruct.*;

import sgu.j2ee.medifamily.dtos.FamilyDoctorDto;
import sgu.j2ee.medifamily.entities.FamilyDoctor;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
		IFamilyMapper.class, IDoctorMapper.class })
public interface FamilyDoctorMapper {
	FamilyDoctor toEntity(FamilyDoctorDto familyDoctorDto);

	FamilyDoctorDto toDto(FamilyDoctor familyDoctor);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	FamilyDoctor partialUpdate(FamilyDoctorDto familyDoctorDto, @MappingTarget FamilyDoctor familyDoctor);

	List<FamilyDoctorDto> toDTO(List<FamilyDoctor> content);
}
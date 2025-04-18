package sgu.j2ee.medifamily.mappers;

import java.util.List;

import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.VaccinationDto;
import sgu.j2ee.medifamily.entities.Vaccination;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Primary
public interface VaccinationMapper {
	Vaccination toEntity(VaccinationDto vaccinationDto);

	VaccinationDto toDto(Vaccination vaccination);

	List<VaccinationDto> toDto(List<Vaccination> vaccination);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Vaccination partialUpdate(VaccinationDto vaccinationDto, @MappingTarget Vaccination vaccination);
}
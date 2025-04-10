package sgu.j2ee.medifamily.mappers;

import org.mapstruct.*;

import sgu.j2ee.medifamily.dtos.PrescriptionDto;
import sgu.j2ee.medifamily.entities.Prescription;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PrescriptionMapper {
	Prescription toEntity(PrescriptionDto prescriptionDto);

	PrescriptionDto toDto(Prescription prescription);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Prescription partialUpdate(PrescriptionDto prescriptionDto, @MappingTarget Prescription prescription);
}
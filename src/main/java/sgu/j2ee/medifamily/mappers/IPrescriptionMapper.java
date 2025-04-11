package sgu.j2ee.medifamily.mappers;

import java.util.List;

import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.PrescriptionDto;
import sgu.j2ee.medifamily.entities.Prescription;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
		IPrescriptionItemMapper.class })
@Primary
public interface IPrescriptionMapper {
	@Mapping(target = "items", source = "items")
	Prescription toEntity(PrescriptionDto prescriptionDto);

	@Mapping(target = "items", source = "items")
	PrescriptionDto toDto(Prescription prescription);

	List<PrescriptionDto> toDto(List<Prescription> prescription);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Prescription partialUpdate(PrescriptionDto prescriptionDto, @MappingTarget Prescription prescription);
}
package sgu.j2ee.medifamily.mappers;

import java.util.List;

import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.PrescriptionItemDto;
import sgu.j2ee.medifamily.entities.PrescriptionItem;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Primary
public interface IPrescriptionItemMapper {
	PrescriptionItem toEntity(PrescriptionItemDto prescriptionItemDto2);

	PrescriptionItemDto toDto(PrescriptionItem prescriptionItem);

	List<PrescriptionItemDto> toDto(List<PrescriptionItem> prescriptionItem);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	PrescriptionItem partialUpdate(PrescriptionItemDto prescriptionItemDto2,
			@MappingTarget PrescriptionItem prescriptionItem);
}
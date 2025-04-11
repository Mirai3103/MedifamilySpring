package sgu.j2ee.medifamily.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.MedicalRecordDto;
import sgu.j2ee.medifamily.entities.MedicalRecord;

@Mapper(componentModel = "spring")
@Primary
public interface IMediaRecordMapper {
	IMediaRecordMapper INSTANCE = Mappers.getMapper(IMediaRecordMapper.class);

	MedicalRecordDto toDTO(MedicalRecord mediaRecord);

	List<MedicalRecordDto> toDTO(List<MedicalRecord> medicalRecords);

	MedicalRecord toEntity(MedicalRecordDto medicalRecordDto);

}

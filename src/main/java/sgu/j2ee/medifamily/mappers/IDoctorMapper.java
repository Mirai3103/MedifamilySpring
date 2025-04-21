package sgu.j2ee.medifamily.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.user.DoctorDTO;
import sgu.j2ee.medifamily.entities.Doctor;

@Mapper(componentModel = "spring")
@Primary
public interface IDoctorMapper {

	IDoctorMapper INSTANCE = Mappers.getMapper(IDoctorMapper.class);

	DoctorDTO toDTO(Doctor doctor);

	Doctor doctorDTOToDoctor(DoctorDTO doctorDTO);

	List<Doctor> doctorDTOToDoctor(List<DoctorDTO> doctorDTO);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Doctor partialUpdate(DoctorDTO doctorDTO, @MappingTarget Doctor doctor);
}
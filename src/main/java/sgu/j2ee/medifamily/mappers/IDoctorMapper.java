package sgu.j2ee.medifamily.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import sgu.j2ee.medifamily.dtos.user.DoctorDTO;
import sgu.j2ee.medifamily.entities.Doctor;

@Mapper(componentModel = "spring")
public interface IDoctorMapper {

    IDoctorMapper INSTANCE = Mappers.getMapper(IDoctorMapper.class);

    DoctorDTO toDTO(Doctor doctor);

    Doctor doctorDTOToDoctor(DoctorDTO doctorDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Doctor partialUpdate(DoctorDTO doctorDTO, @MappingTarget Doctor doctor);
}
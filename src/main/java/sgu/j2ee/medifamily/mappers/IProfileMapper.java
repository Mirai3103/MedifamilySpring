package sgu.j2ee.medifamily.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.user.ProfileDTO;
import sgu.j2ee.medifamily.entities.Profile;

@Mapper(componentModel = "spring")
@Primary
public interface IProfileMapper {

	IProfileMapper INSTANCE = Mappers.getMapper(IProfileMapper.class);

	ProfileDTO toDTO(Profile profile);

	Profile profileDTOToProfile(ProfileDTO profileDTO);
}
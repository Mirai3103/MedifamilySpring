package sgu.j2ee.medifamily.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.user.UserDTO;
import sgu.j2ee.medifamily.entities.User;

@Mapper(componentModel = "spring")
@Primary
public interface IUserMapper {

	IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

	sgu.j2ee.medifamily.dtos.user.UserDTO toDTO(User user);

	User userDTOToUser(UserDTO userDTO);
}
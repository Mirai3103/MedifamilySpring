package sgu.j2ee.medifamily.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.family.FamilyMemberDTO;
import sgu.j2ee.medifamily.entities.FamilyMember;

@Mapper(componentModel = "spring")
@Primary
public interface IFamilyMemberMapper {

	IFamilyMemberMapper INSTANCE = Mappers.getMapper(IFamilyMemberMapper.class);

	sgu.j2ee.medifamily.dtos.family.FamilyMemberDTO toDTO(FamilyMember familyMember);

	List<FamilyMemberDTO> toDTOs(List<FamilyMember> familyMembers);

	FamilyMember familyMemberDTOToFamilyMember(FamilyMemberDTO familyMemberDTO);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	FamilyMember partialUpdate(FamilyMemberDTO familyMemberDTO, @MappingTarget FamilyMember familyMember);
}
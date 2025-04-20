package sgu.j2ee.medifamily.mappers;

import java.util.List;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import sgu.j2ee.medifamily.dtos.family.FamilyDTO;
import sgu.j2ee.medifamily.entities.Family;

@Mapper(componentModel = "spring")
@Primary
public interface IFamilyMapper {

	IFamilyMapper INSTANCE = Mappers.getMapper(IFamilyMapper.class);

	@Mapping(source = "owner", target = "owner") // Map Profile to ProfileDTO
	@Mapping(source = "familyMembers", target = "familyMembers")
		// Map FamilyMember to FamilyMemberDTO
	FamilyDTO toDTO(Family family);

	List<FamilyDTO> familiesToFamilyDTOs(List<Family> families);

	@Mapping(source = "owner", target = "owner")
		// Map ProfileDTO to Profile
	Family familyDTOToFamily(FamilyDTO familyDTO);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Family partialUpdate(FamilyDTO familyDTO, @MappingTarget Family family);
}
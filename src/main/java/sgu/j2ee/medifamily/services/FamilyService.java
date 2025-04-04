package sgu.j2ee.medifamily.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.family.CreateFamilyRequest;
import sgu.j2ee.medifamily.entities.Family;
import sgu.j2ee.medifamily.entities.FamilyMember;
import sgu.j2ee.medifamily.exceptions.UnAuthorizedException;
import sgu.j2ee.medifamily.repositories.FamilyMemberRepository;
import sgu.j2ee.medifamily.repositories.FamilyRepository;
import sgu.j2ee.medifamily.repositories.ProfileRepository;

@Service
@RequiredArgsConstructor
public class FamilyService {
	private final FamilyRepository familyRepository;
	private final ProfileRepository profileRepository;
	private final FamilyMemberRepository familyMemberRepository;

	public Family createFamily(CreateFamilyRequest family) {
		System.out.println(family);
		var user = profileRepository
				.findFirstByUserId(family.getCreatedBy())
				.orElseThrow(() -> new UnAuthorizedException("Người dùng không tồn tại"));

		if (user == null) {
			return null;
		}
		var newFamily = Family.builder()
				.familyName(family.getFamilyName())
				.address(family.getAddress())
				.email(family.getEmail())
				.phoneNumber(family.getPhoneNumber())
				.owner(user)
				.isActive(true)
				.build();
		newFamily = familyRepository.save(newFamily);
		FamilyMember owner = FamilyMember.builder()
				.family(newFamily)
				.relationship("Chủ hộ")
				.profile(user)
				.build();
		familyMemberRepository.save(owner);

		return newFamily;
	}

	public Family getFamilyById(Long id) {
		var family = familyRepository.findById(id).orElse(null);
		if (family == null) {
			return null;
		}
		var familyMembers = familyMemberRepository.findByFamilyId(id);
		familyMembers.forEach((member) -> member.setFamily(null));
		family.setFamilyMembers(familyMembers);
		return family;
	}

	public Family updateFamily(Long id, CreateFamilyRequest family) {
		var existingFamily = familyRepository.findById(id).orElse(null);
		if (existingFamily == null) {
			return null;
		}
		existingFamily.setFamilyName(family.getFamilyName());
		existingFamily.setAddress(family.getAddress());
		existingFamily.setEmail(family.getEmail());
		existingFamily.setPhoneNumber(family.getPhoneNumber());
		return familyRepository.save(existingFamily);
	}

	public void deleteFamily(Long id) {
		familyRepository.deleteById(id);
	}

	public List<Family> getFamiliesByUserId(Long userId) {
		return familyRepository.findByFamilyUserId(userId);
	}
}

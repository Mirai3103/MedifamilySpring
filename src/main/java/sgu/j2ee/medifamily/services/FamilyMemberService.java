package sgu.j2ee.medifamily.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.family.AddMemberToFamilyRequest;
import sgu.j2ee.medifamily.entities.FamilyMember;
import sgu.j2ee.medifamily.entities.Profile;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.FamilyMemberRepository;
import sgu.j2ee.medifamily.repositories.FamilyRepository;
import sgu.j2ee.medifamily.repositories.ProfileRepository;

@RequiredArgsConstructor
@Service
public class FamilyMemberService {
	private final FamilyMemberRepository familyMemberRepository;
	private final ProfileRepository profileRepository;
	private final FamilyRepository familyRepository;

	public List<FamilyMember> getMembersByFamilyId(Long familyId) {
		return familyMemberRepository.findByFamilyId(familyId);
	}

	public FamilyMember addMemberToFamily(AddMemberToFamilyRequest familyMember) {
		var family = familyRepository
				.findById(familyMember.getFamilyId())
				.orElseThrow(() -> new NotFoundException("Gia đình không tồn tại"));

		if (familyMember.isHasAccount()) {
			var user = profileRepository
					.findFirstByUserId(familyMember.getAccountId())
					.orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
			var member = FamilyMember.builder()
					.family(family)
					.profile(user)
					.relationship(familyMember.getRelationship())
					.build();
			return familyMemberRepository.save(member);
		}

		var memberProfile = familyMember.getMemberProfile();
		Profile profile = Profile.builder()
				.email(memberProfile.getEmail())
				.fullName(memberProfile.getFullName())
				.gender(memberProfile.getGender())
				.phoneNumber(memberProfile.getPhoneNumber())
				.user(null) // Chưa có tài khoản
				.dateOfBirth(memberProfile.getBirthDate()).build();
		profileRepository.save(profile);
		var member = FamilyMember.builder()
				.family(family)
				.relationship(familyMember.getRelationship())
				.profile(profile)
				.isActive(true)
				.build();
		return familyMemberRepository.save(member);
	}
}

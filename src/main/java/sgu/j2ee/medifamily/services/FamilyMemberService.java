package sgu.j2ee.medifamily.services;

import java.util.List;

import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import sgu.j2ee.medifamily.dtos.family.AddMemberToFamilyRequest;
import sgu.j2ee.medifamily.entities.FamilyMember;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.FamilyMemberRepository;
import sgu.j2ee.medifamily.repositories.FamilyRepository;
import sgu.j2ee.medifamily.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class FamilyMemberService {
    private final FamilyMemberRepository familyMemberRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public List<FamilyMember> getMembersByFamilyId(Long familyId) {
        return familyMemberRepository.findByFamilyId(familyId);
    }

    public FamilyMember addMemberToFamily(AddMemberToFamilyRequest familyMember) {
        var family = familyRepository.findById(familyMember.getFamilyId()).orElseThrow(()-> new NotFoundException("Gia đình không tồn tại"));

        if(familyMember.isHasAccount()) {
            var user = userRepository.findById(familyMember.getAccountId()).orElseThrow(()-> new NotFoundException("Người dùng không tồn tại"));
            var member = FamilyMember.builder()
                    .family(family)
                    .user(user)
                    .isHasAccount(true)
                    .relationship(familyMember.getRelationship())
                    .build();
            return familyMemberRepository.save(member);
        }

        var memberProfile = familyMember.getMemberProfile();
        var member = FamilyMember.builder()
                .family(family)
                .relationship(familyMember.getRelationship())
                .email(memberProfile.getEmail())
                .fullName(memberProfile.getFullName())
                .gender(memberProfile.getGender())
                .phoneNumber(memberProfile.getPhoneNumber())
                .dateOfBirth(memberProfile.getBirthDate())
                .isActive(true)
                .user(null)
                .isHasAccount(false)
                .build();
        return familyMemberRepository.save(member);            
    }


}

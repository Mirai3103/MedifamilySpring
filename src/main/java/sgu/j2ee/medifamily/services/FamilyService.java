package sgu.j2ee.medifamily.services;

import java.util.List;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.CreateFamilyRequest;
import sgu.j2ee.medifamily.entities.Family;
import sgu.j2ee.medifamily.entities.FamilyMember;
import sgu.j2ee.medifamily.repositories.FamilyMemberRepository;
import sgu.j2ee.medifamily.repositories.FamilyRepository;
import sgu.j2ee.medifamily.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
private final UserRepository userRepository;
private final FamilyMemberRepository familyMemberRepository;
    
    public Family createFamily(CreateFamilyRequest family) {
        var user = userRepository.findById(family.getCreatedBy()).orElse(null);
        if (user == null) {
            return null;
        }
        var newFamily = Family.builder()
                .familyName(family.getFamilyName())
                .address(family.getAddress())
                .email(family.getEmail())
                .phoneNumber(family.getPhoneNumber())
                .owner(user)
                .build();
        newFamily = familyRepository.save(newFamily);
        FamilyMember owner = FamilyMember.builder()
                .family(newFamily)
                .user(user)
                .build();
        familyMemberRepository.save(owner);

        return newFamily;
    }

    public Family getFamilyById(Long id) {
        return familyRepository.findById(id).orElse(null);
    }

    public Family updateFamily(Family family) {
        var existingFamily = familyRepository.findById(family.getId()).orElse(null);
        if (existingFamily == null) {
            return null;
        }
        return familyRepository.save(family);
    }

    public void deleteFamily(Long id) {
        familyRepository.deleteById(id);
    }

    public List<Family> getFamiliesByUserId(Long userId) {
        return familyRepository.findByFamilyUserId(userId);
    }

}

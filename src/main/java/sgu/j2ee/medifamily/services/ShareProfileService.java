package sgu.j2ee.medifamily.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.ShareProfileQuery;
import sgu.j2ee.medifamily.entities.ShareProfile;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.FamilyMemberRepository;
import sgu.j2ee.medifamily.repositories.FamilyRepository;
import sgu.j2ee.medifamily.repositories.SharePermissionRepository;
import sgu.j2ee.medifamily.repositories.ShareProfileRepository;

@Service
@RequiredArgsConstructor
public class ShareProfileService {
	private final ShareProfileRepository shareProfileRepository;
	private final SharePermissionRepository sharePermissionRepository;
	private final FamilyMemberRepository familyMemberRepository;
	private final FamilyRepository familyRepository;

	public ShareProfile shareProfile(ShareProfile shareProfile) {
		var family = familyRepository.findById(shareProfile.getFamilyId());
		if (family.isEmpty()) {
			throw new NotFoundException("Family not found");
		}
		var listPermission = shareProfile.getSharePermissions();
		shareProfile.setSharePermissions(null);
		shareProfile.setFamily(family.get());
		if (shareProfile.getMemberId() != null) {
			var familyMember = familyMemberRepository.findByFamilyIdAndId(shareProfile.getFamilyId(), shareProfile.getMemberId()).orElseThrow(
					() -> new NotFoundException("Family member not found"));
			shareProfile.setMember(familyMember);
		} else {
			shareProfile.setMember(null);
		}
		var share = shareProfileRepository.save(shareProfile);
		sharePermissionRepository
				.saveAll(listPermission.stream().peek(permission -> permission.setShareRequest(share)).toList());
		return share;
	}

	public Optional<ShareProfile> getShareProfileById(UUID id) {
		return shareProfileRepository.findById(id);
	}

	public List<ShareProfile> getAllShareProfiles(ShareProfileQuery query) {
		return shareProfileRepository.findAll(query.toSpecification());
	}

	public void deleteShareProfile(UUID id) {
		var shareProfile = shareProfileRepository.findById(id);
		if (shareProfile.isEmpty()) {
			throw new NotFoundException("Share profile not found");
		}
		sharePermissionRepository.deleteAll(shareProfile.get().getSharePermissions());
		shareProfileRepository.delete(shareProfile.get());
	}
}

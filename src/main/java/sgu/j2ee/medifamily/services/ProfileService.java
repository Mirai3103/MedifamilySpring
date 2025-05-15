package sgu.j2ee.medifamily.services;

import java.io.IOException;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.user.UpdateHealthProfile;
import sgu.j2ee.medifamily.dtos.user.UpdateProfileRequest;
import sgu.j2ee.medifamily.entities.Profile;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.ProfileRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {
	private final ProfileRepository profileRepository;
	private final FileService fileService;
	private AuditorAware<String> auditorAware;

	public Profile updateUserBasicProfile(UpdateProfileRequest request) {
		var user = profileRepository
				.findById(request.getId())
				.orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));

		user.setEmail(request.getEmail());
		user.setFullName(request.getFullName());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setDateOfBirth(request.getDateOfBirth());
		user.setGender(request.getGender());
		user.setAddress(request.getAddress());
		user.setBio(request.getBio());
		user.setDateOfBirth(request.getDateOfBirth());

		return profileRepository.save(user);
	}

	public Profile getProfileById(Long id) {
		return profileRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
	}

	public Profile updateHealthProfile(UpdateHealthProfile healthProfile) {
		Profile profile = profileRepository
				.findById(healthProfile.getId())
				.orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
		profile.setBloodType(healthProfile.getBloodType());
		profile.setHeight(healthProfile.getHeight());
		profile.setWeight(healthProfile.getWeight());
		profile.setAllergies(healthProfile.getAllergies());
		profile.setChronicDiseases(healthProfile.getChronicDiseases());
		profile.setNotes(healthProfile.getNotes());
		profile.setHealthInsuranceNumber(healthProfile.getHealthInsuranceNumber());
		return profileRepository.save(profile);

	}

	public Profile updateMyAvatar(MultipartFile file) throws IOException {
		var avatarUrl = fileService.uploadFile(file).getServerPath();
		var profile = profileRepository
				.findFirstByUserId(Long.parseLong(auditorAware.getCurrentAuditor()
						.orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"))))
				.orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
		fileService.deleteFile(profile.getAvatarUrl());
		profile.setAvatarUrl(avatarUrl);
		return profileRepository.save(profile);
	}

	public Profile updateUserAvatar(Long id, String avatarUrl) {
		var profile = profileRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
		fileService.deleteFile(profile.getAvatarUrl());
		profile.setAvatarUrl(avatarUrl);
		return profileRepository.save(profile);
	}
}

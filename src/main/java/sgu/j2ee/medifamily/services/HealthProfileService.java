package sgu.j2ee.medifamily.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.user.UpdateHealthProfile;
import sgu.j2ee.medifamily.entities.HealthProfile;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.HealthProfileRepository;
import sgu.j2ee.medifamily.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class HealthProfileService {
	private final HealthProfileRepository healthProfileRepository;
	private final UserRepository userRepository;

	public HealthProfile updateHealthProfile(UpdateHealthProfile healthProfile) {
		HealthProfile profile = this.getHealthProfileById(healthProfile.getId());
		profile.setBloodType(healthProfile.getBloodType());
		profile.setHeight(healthProfile.getHeight());
		profile.setWeight(healthProfile.getWeight());
		profile.setAllergies(healthProfile.getAllergies());
		profile.setChronicDiseases(healthProfile.getChronicDiseases());
		profile.setNotes(healthProfile.getNotes());
		profile.setHealthInsuranceNumber(healthProfile.getHealthInsuranceNumber());
		return healthProfileRepository.save(profile);

	}

	public HealthProfile getHealthProfileById(long healthProfileId) {
		var healthProfile = healthProfileRepository.findById(healthProfileId);
		if (healthProfile.isPresent()) {
			return healthProfile.get();
		}
		var isExist = userRepository.existsById(healthProfileId);
		if (isExist) {
			// create new health profile
			var newHealthProfile = new HealthProfile();
			newHealthProfile.setId(healthProfileId);
			return healthProfileRepository.save(newHealthProfile);
		}
		throw new NotFoundException("Thông tin người dùng không tồn tại");
	}
}

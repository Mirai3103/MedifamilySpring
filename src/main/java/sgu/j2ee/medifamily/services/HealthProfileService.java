package sgu.j2ee.medifamily.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.user.UpdateHealthProfile;
import sgu.j2ee.medifamily.entities.HealthProfile;
import sgu.j2ee.medifamily.repositories.HealthProfileRepository;

@Service
@RequiredArgsConstructor
public class HealthProfileService {
	private final HealthProfileRepository healthProfileRepository;

	public HealthProfile updateHealthProfile(UpdateHealthProfile healthProfile) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateHealthProfile'");
	}

	public HealthProfile getHealthProfileById(long healthProfileId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getHealthProfileById'");
	}
}

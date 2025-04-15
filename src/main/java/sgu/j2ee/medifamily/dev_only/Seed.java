package sgu.j2ee.medifamily.dev_only;

import org.springframework.shell.standard.ShellComponent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sgu.j2ee.medifamily.repositories.*;
import sgu.j2ee.medifamily.services.*;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class Seed {

	private final UserDetailsServiceImpl userDetailsService;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final JwtService jwtService;
	private final FamilyRepository familyRepository;
	private final FamilyMemberRepository familyMemberRepository;
	private final DoctorRepository doctorRepository;
	private final ProfileRepository profileRepository;
	private final HealthMetricRepository healthMetricRepository;
	private final MedicalRecordRepository medicalRecordRepository;
	private final PrescriptionItemRepository prescriptionItemRepository;
	private final PrescriptionRepository prescriptionRepository;

}
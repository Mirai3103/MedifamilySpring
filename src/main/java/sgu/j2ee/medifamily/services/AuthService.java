package sgu.j2ee.medifamily.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sgu.j2ee.medifamily.dtos.AuthenticationResponse;
import sgu.j2ee.medifamily.dtos.LoginResponse;
import sgu.j2ee.medifamily.dtos.RegisterRequest;
import sgu.j2ee.medifamily.entities.Doctor;
import sgu.j2ee.medifamily.entities.Profile;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.mappers.IUserMapper;
import sgu.j2ee.medifamily.repositories.DoctorRepository;
import sgu.j2ee.medifamily.repositories.ProfileRepository;
import sgu.j2ee.medifamily.repositories.UserRepository;

@Service
@AllArgsConstructor
public class AuthService {
	private final UserDetailsServiceImpl userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final DoctorRepository doctorRepository;
	private final JwtService jwtUtil;
	private final AuthenticationManager authenticationManager;
	private final ProfileRepository profileRepository;
	private final IUserMapper userMapper;

	public User register(RegisterRequest registerDTO) {
		var user = User.builder()
				.password(passwordEncoder.encode(registerDTO.getPassword()))
				.email(registerDTO.getEmail())

				.build();
		user = userRepository.save(user);
		var profile = Profile.builder().email(registerDTO.getEmail())
				.dateOfBirth(registerDTO.getDateOfBirth())
				.fullName(registerDTO.getFullName())
				.gender(registerDTO.getGender())
				.user(user)
				.build();
		profile = profileRepository.save(profile);

		if (registerDTO.getIsDoctor()) {
			var doctorInfo = registerDTO.getDoctor();
			var doctor = Doctor.builder()
					.user(user)
					.bio(doctorInfo.getBio())
					.licenseNumber(doctorInfo.getLicenseNumber())
					.medicalFacility(doctorInfo.getMedicalFacility())
					.specialty(doctorInfo.getSpecialty())
					.isVerified(true)
					.build();
			doctorRepository.save(doctor);
		}
		return user;
	}

	public LoginResponse login(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		var userDetails = userDetailsService.loadUserByUsername(username);
		var token = jwtUtil.generateToken((User) userDetails);
		return LoginResponse.builder()
				.token(AuthenticationResponse.builder().token(token).build())
				.user(userMapper.toDTO((User) userDetails))
				.build();
	}
}

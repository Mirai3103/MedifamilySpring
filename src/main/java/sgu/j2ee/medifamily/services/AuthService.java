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
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.repositories.DoctorRepository;
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


    public User register(RegisterRequest registerDTO) {
        var user = User.builder()
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .dateOfBirth(registerDTO.getDateOfBirth())
                .fullName(registerDTO.getFullName())
                .gender(registerDTO.getGender())
                .build();
        user = userRepository.save(user);
        if (registerDTO.isDoctor()) {
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
                .user((User) userDetails)
                .build();
    }

}

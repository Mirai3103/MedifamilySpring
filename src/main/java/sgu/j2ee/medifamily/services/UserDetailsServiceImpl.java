package sgu.j2ee.medifamily.services;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sgu.j2ee.medifamily.dtos.LoginResponseDTO;
import sgu.j2ee.medifamily.dtos.RegisterDTO;
import sgu.j2ee.medifamily.dtos.TokenDTO;
import sgu.j2ee.medifamily.entities.Doctor;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.repositories.DoctorRepository;
import sgu.j2ee.medifamily.repositories.UserRepository;
import sgu.j2ee.medifamily.utils.JwtUtil;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        boolean isNumber = NumberUtils.isDigits(username);
        if (isNumber) {
            return userRepository.findById(Long.parseLong(username))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + username));
        }
        return userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    public User register(RegisterDTO registerDTO) {
        var user = User.builder()
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .dateOfBirth(registerDTO.getDateOfBirth())
                .fullName(registerDTO.getFullName())
                .username(registerDTO.getUsername())
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

    public LoginResponseDTO login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var userDetails = loadUserByUsername(username);
        var token = jwtUtil.generateToken((User) userDetails);
        return LoginResponseDTO.builder()
                .token(TokenDTO.builder().token(token).build())
                .user((User) userDetails)
                .build();
    }
}

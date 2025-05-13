package sgu.j2ee.medifamily.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sgu.j2ee.medifamily.dtos.AuthenticationResponse;
import sgu.j2ee.medifamily.dtos.LoginResponse;
import sgu.j2ee.medifamily.dtos.RegisterRequest;
import sgu.j2ee.medifamily.dtos.ResetPasswordDTO;
import sgu.j2ee.medifamily.entities.Doctor;
import sgu.j2ee.medifamily.entities.Profile;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.mappers.IUserMapper;
import sgu.j2ee.medifamily.repositories.DoctorRepository;
import sgu.j2ee.medifamily.repositories.ProfileRepository;
import sgu.j2ee.medifamily.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final JwtService jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ProfileRepository profileRepository;
    private final IUserMapper userMapper;
    private final JavaMailSender mailSender;

    @Value("${frontend.reset-password-url}")
    private String resetPasswordUrl;

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

    public void sendPasswordResetEmail(String email) {
        var user = userRepository.findFirstByUsername(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        String token = jwtUtil.generateResetPasswordToken(user);
        String resetLink = String.format("%s?token=%s", resetPasswordUrl, token);


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(user.getEmail());
            helper.setSubject("Đặt lại mật khẩu");
            helper.setFrom("no-reply@test-r83ql3ppr0xgzw1j.mlsender.net");
            helper.setText(
                    "<html><body>" +
                            "<h1>Đặt lại mật khẩu</h1>" +
                            "<p>Nhấp vào liên kết bên dưới để đặt lại mật khẩu của bạn:</p>" +
                            "<a href=\"" + resetLink + "\">Đặt lại mật khẩu</a>" +
                            "</body></html>",
                    true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);

    }

    public String validatePasswordResetToken(String token) {
        return jwtUtil.extractUsername(token);

    }

    public void resetPassword(ResetPasswordDTO dto) {
        var idStr = jwtUtil.extractUsername(dto.getToken());
        var user = userRepository.findById(Long.valueOf(idStr))
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }
}

package sgu.j2ee.medifamily.services;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import sgu.j2ee.medifamily.dtos.LoginResponse;
import sgu.j2ee.medifamily.dtos.RegisterRequest;
import sgu.j2ee.medifamily.dtos.AuthenticationResponse;
import sgu.j2ee.medifamily.entities.Doctor;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.exceptions.RequireLoginException;
import sgu.j2ee.medifamily.repositories.DoctorRepository;
import sgu.j2ee.medifamily.repositories.UserRepository;


import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
 
    private AuditorAware<String> auditorAware;


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


    public User getCurrentUser() {
        log.info("Getting current user");
        var userId = auditorAware.getCurrentAuditor().orElseThrow(() -> new RequireLoginException("No user found"));
        return userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new RequireLoginException("User not found"));
    }
}

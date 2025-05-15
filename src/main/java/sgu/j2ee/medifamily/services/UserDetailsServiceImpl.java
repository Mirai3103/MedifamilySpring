package sgu.j2ee.medifamily.services;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sgu.j2ee.medifamily.dtos.user.UpdatePasswordRequest;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.exceptions.UnAuthorizedException;
import sgu.j2ee.medifamily.repositories.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private AuditorAware<String> auditorAware;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		boolean isNumber = NumberUtils.isDigits(username);
		if (isNumber) {
			return userRepository
					.findById(Long.parseLong(username))
					.orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + username));
		}
		return userRepository
				.findFirstByUsername(username)
				.orElseThrow(
						() -> new UsernameNotFoundException("User not found with username: " + username));
	}

	public User getCurrentUser() {
		log.info("Getting current user");
		var userId = auditorAware
				.getCurrentAuditor()
				.orElseThrow(() -> new UnAuthorizedException("No user found"));
		return userRepository
				.findById(Long.parseLong(userId))
				.orElseThrow(() -> new UnAuthorizedException("User not found"));
	}

	public User updateMyPassword(@Valid UpdatePasswordRequest newPassword) {
		var user = getCurrentUser();

		if (!passwordEncoder.matches(newPassword.getOldPassword(), user.getPassword())) {
			throw new UnAuthorizedException("Old password does not match");
		}
		user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
		return userRepository.save(user);
	}

}

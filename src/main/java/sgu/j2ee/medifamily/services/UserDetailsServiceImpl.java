package sgu.j2ee.medifamily.services;

import java.io.IOException;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sgu.j2ee.medifamily.dtos.user.UpdatePasswordRequest;
import sgu.j2ee.medifamily.dtos.user.UpdateProfileRequest;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.exceptions.UnAuthorizedException;
import sgu.j2ee.medifamily.repositories.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private AuditorAware<String> auditorAware;
	private FileService fileService;

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

	public User updateUserProfile(UpdateProfileRequest request) {
		var user = userRepository
				.findById(request.getId())
				.orElseThrow(() -> new NotFoundException("User not found"));

		user.setEmail(request.getEmail());
		user.setFullName(request.getFullName());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setDateOfBirth(request.getDateOfBirth());
		user.setGender(request.getGender());
		user.setAddress(request.getAddress());
		user.setBio(request.getBio());
		user.setDateOfBirth(request.getDateOfBirth());

		return userRepository.save(user);
	}

	public User updateMyAvatar(MultipartFile file) throws IOException {
		var avatarUrl = fileService.uploadFile(file).getServerPath();
		var user = getCurrentUser();
		fileService.deleteFile(user.getAvatarUrl());
		user.setAvatarUrl(avatarUrl);
		return userRepository.save(user);
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

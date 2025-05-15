package sgu.j2ee.medifamily.configs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.RegisterRequest;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.services.AuthService;
import sgu.j2ee.medifamily.services.JwtService;
import sgu.j2ee.medifamily.services.ProfileService;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	private final JwtService jwtService; // service tạo JWT
	private final AuthService authService; // nếu cần lưu user
	private final ObjectMapper objectMapper;
	private final ProfileService profileService;
	private final UserDetailsServiceImpl userDetailsService;
	@Value("${frontend.url}")
	private String clientUrl = "http://localhost:3001"; // URL của frontend

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		OAuth2User oauthUser = oauthToken.getPrincipal();

		String email = oauthUser.getAttribute("email");
		String name = oauthUser.getAttribute("name");
		String picture = oauthUser.getAttribute("picture");
		// Check hoặc tạo user trong hệ thống của bạn
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setEmail(email);
		registerRequest.setFullName(name);
		registerRequest.setPassword("Password@123");
		registerRequest.setDateOfBirth(null);
		registerRequest.setGender(null);
		boolean isNewUser = false;
		User user = (User) userDetailsService.loadUserByUsername(email);
		if (user == null) {
			user = authService.register(registerRequest);
			profileService.updateUserAvatar(user.getId(), picture);
			isNewUser = true;

		}

		// Sinh JWT
		String jwt = jwtService.generateToken(user);

		// Trả về JWT cho client bằng cách redirect
		String redirectUrl = clientUrl + "/callback/google?token=" + jwt + "&isNewUser=" + isNewUser;
		response.sendRedirect(redirectUrl);

	}
}

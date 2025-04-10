package sgu.j2ee.medifamily.dev_only;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sgu.j2ee.medifamily.dtos.RegisterRequest;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.entities.enums.Gender;
import sgu.j2ee.medifamily.repositories.UserRepository;
import sgu.j2ee.medifamily.services.AuthService;
import sgu.j2ee.medifamily.services.JwtService;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class MyCommands {

	private final UserDetailsServiceImpl userDetailsService;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final JwtService jwtService;

	@ShellMethod("Gen Jwt Token")
	public String genJwtToken() {
		// if not have any user then create one
		var count = userRepository.count();
		User user = null;
		if (count == 0) {
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.setPassword("Kaito@1412");
			registerRequest.setFullName("Nguyen Van A");
			registerRequest.setEmail("huuhoag1412@gmail.com");
			registerRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
			registerRequest.setGender(Gender.MALE);
			user = authService.register(registerRequest);
		} else {
			user = userRepository.findAll(Pageable.ofSize(1)).getContent().get(0);
		}
		return jwtService.generateToken(user);
	}

	@ShellMethod("Gen OpenApi")
	public void genOpenApi() {
		try {
			if ("prod".equals(System.getProperty("spring.profiles.active"))) {
				log.info("Skipping openapi.json generation in production mode.");
				return;
			}

			log.info("Generating openapi.json file...");
			Thread.sleep(2000);

			RestTemplate restTemplate = new RestTemplate();
			String apiUrl = "http://localhost:8080" + "/v3/api-docs";
			String openApiContent = restTemplate.getForObject(apiUrl, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String prettyJson = objectMapper.writeValueAsString(objectMapper.readTree(openApiContent));

			Files.write(Paths.get("openapi.json"), prettyJson.getBytes());
			log.info("Successfully generated prettified openapi.json file.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
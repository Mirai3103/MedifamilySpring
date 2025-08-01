package sgu.j2ee.medifamily.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(
						new Info()
								.title("API Documentation")
								.version("1.0")
								.description("API với xác thực Bearer Token"))
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(
						new io.swagger.v3.oas.models.Components()
								.addSecuritySchemes(
										"bearerAuth",
										new SecurityScheme()
												.name("bearerAuth")
												.type(SecurityScheme.Type.HTTP)
												.scheme("bearer")
												.bearerFormat("JWT")));
	}
}

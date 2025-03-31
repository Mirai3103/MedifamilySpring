package sgu.j2ee.medifamily.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Controller
// @Order(Ordered.LOWEST_PRECEDENCE)
public class SPAController {
	private static final List<String> EXCLUDED_PATHS = Arrays.asList(
			"/api/",
			"/swagger-ui.html",
			"/swagger-ui/",
			"/v2/api-docs",
			"/v3/api-docs",
			"/swagger-resources",
			"/configuration/ui",
			"/configuration/security",
			"/webjars/");

	@RequestMapping(value = "/**")
	@Order(Ordered.LOWEST_PRECEDENCE)
	public String forward(HttpServletRequest request) {
		String path = request.getRequestURI();

		// Kiểm tra nếu là file có extension
		if (path.contains(".") && !path.endsWith("/")) {
			return null;
		}

		// Kiểm tra xem đường dẫn có thuộc vào danh sách loại trừ không
		for (String excludedPath : EXCLUDED_PATHS) {
			if (path.startsWith(excludedPath)) {
				log.info("Excluded path: {}", path);
				return null;
			}
		}

		return "forward:/index.html";
	}
}

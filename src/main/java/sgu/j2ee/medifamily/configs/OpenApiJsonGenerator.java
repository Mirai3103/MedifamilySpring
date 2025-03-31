package sgu.j2ee.medifamily.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Slf4j
public class OpenApiJsonGenerator {

    @Value("${server.port:8080}")
    private String serverPort;

    @EventListener(ApplicationReadyEvent.class)
    public void generateOpenApiJson() {
        try {
            if ("prod".equals(System.getProperty("spring.profiles.active"))) {
                log.info("Skipping openapi.json generation in production mode.");
                return;
            }

            log.info("Generating openapi.json file...");
            Thread.sleep(2000);

            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "http://localhost:" + serverPort + "/v3/api-docs";
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
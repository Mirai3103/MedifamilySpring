package sgu.j2ee.medifamily.configs;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class S3ClientConfig {

	@Value("${spring.content.s3.bucket}")
	private String bucketName;
	@Value("${spring.content.s3.accessKey}")
	private String accessKey;
	@Value("${spring.content.s3.secretKey}")
	private String secretKey;

	@Value("${spring.content.s3.endpoint}")
	private String endpoint;

	@Bean
	public S3Client s3Client() {
		return S3Client.builder()
				.endpointOverride(URI.create(endpoint))
				.region(Region.AP_NORTHEAST_1)
				.credentialsProvider(
						StaticCredentialsProvider.create(
								AwsBasicCredentials.create(accessKey, secretKey)))

				.serviceConfiguration(S3Configuration.builder()
						.pathStyleAccessEnabled(true) // quan trọng với MinIO
						.build())
				.build();
	}
}

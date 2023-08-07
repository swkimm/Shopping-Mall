package com.example.shop.config;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Data
@Configuration
public class AppConfig {

    // AWS 설정
    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;


    @Value("${aws.bucketUrl}")
    private String bucketUrl;

    @Autowired
    private ServletContext application;

    @PostConstruct
    public void init() {
        application.setAttribute("bucketUrl", bucketUrl);
    }

    @Bean
    public S3Client s3client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        AwsCredentialsProvider provider = StaticCredentialsProvider.create(credentials);

        S3Client s3client = S3Client.builder()
                .credentialsProvider(provider)
                .region(Region.AP_NORTHEAST_2)
                .build();

        return s3client;
    }

}

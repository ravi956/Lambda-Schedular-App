package com.example.schedule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.cloudwatchevents.CloudWatchEventsClient;
import software.amazon.awssdk.services.lambda.LambdaClient;

@Configuration
public class AwsCloudWatchEventsClientConfig {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    private AwsBasicCredentials awsCredentials() {
        return AwsBasicCredentials.create(accessKey, secretKey);
    }

    @Bean
    public CloudWatchEventsClient cweClient() {
        return CloudWatchEventsClient
                .builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials()))
                .build();
    }

    @Bean
    public LambdaClient lambdaClient() {
        return LambdaClient
                .builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials()))
                .build();
    }
}

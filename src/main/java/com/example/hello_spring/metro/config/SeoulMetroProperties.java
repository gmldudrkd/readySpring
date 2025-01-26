package com.example.hello_spring.metro.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "external-server.seoul-metro.api")
public record SeoulMetroProperties(
    String baseUrl,
    String accessKey,
    Duration connectTimeout,
    Duration readTimeout
) {

}
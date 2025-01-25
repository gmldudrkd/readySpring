package com.example.hello_spring.metro.config;

import com.example.hello_spring.metro.exception.MetroApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class ExternalApiConfig {
    // RestClient 사용하는 도메인 별 공통 config
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestClient seoulMetroApiRestClient() {
        return RestClient.builder()
            .defaultHeaders(headers -> {
                headers.setContentType(MediaType.APPLICATION_XML);
            })
            .defaultStatusHandler(HttpStatusCode::isError,
                (request, response) ->{
                    throw new MetroApiException();
                })
            .build();
    }
}

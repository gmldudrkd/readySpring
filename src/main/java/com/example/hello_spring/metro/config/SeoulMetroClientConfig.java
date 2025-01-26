package com.example.hello_spring.metro.config;

import com.example.hello_spring.global.enums.ErrorCode;
import com.example.hello_spring.global.exception.ExternalInterfaceException;
import com.example.hello_spring.metro.client.SeoulMetroInterfaceClient;
import java.net.http.HttpClient;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class SeoulMetroClientConfig {
    // SeoulMetro API RestClient 사용하는 공통 config

    private final SeoulMetroProperties seoulMetroProperties;

    private JdkClientHttpRequestFactory createRequestFactory() {
        // HTTP 클라이언트 생성 및 설정
        var httpClient = HttpClient.newBuilder()
            .connectTimeout(seoulMetroProperties.connectTimeout())
            .build();
        var factory = new JdkClientHttpRequestFactory(
            httpClient,
            Executors.newVirtualThreadPerTaskExecutor()
        );
        factory.setReadTimeout(seoulMetroProperties.readTimeout());
        return factory;
    }

    @Bean
    public RestClient seoulMetroApiRestClient() {
        return RestClient.builder()
            .baseUrl(seoulMetroProperties.baseUrl()+seoulMetroProperties.accessKey())
            .requestFactory(createRequestFactory())
            .defaultHeaders(headers -> headers.setContentType(MediaType.APPLICATION_XML))
            .defaultStatusHandler(HttpStatusCode::isError,
                (request, response) ->{
                    throw ExternalInterfaceException.of(
                        ErrorCode.SEOUL_METRO_API_CALL_FAIL, response.getBody().toString()
                    );
                })
            .build();
    }

    @Bean
    public SeoulMetroInterfaceClient seoulMetroInterfaceClient(RestClient seoulMetroApiRestClient) {
        var factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(seoulMetroApiRestClient))
            .build();
        return factory.createClient(SeoulMetroInterfaceClient.class);
    }
}

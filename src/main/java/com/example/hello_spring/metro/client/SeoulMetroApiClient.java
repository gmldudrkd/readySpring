package com.example.hello_spring.metro.client;

import com.example.hello_spring.metro.dto.MetroApiResponse;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiErrorResponse;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiRequest;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiResponse;
import com.example.hello_spring.metro.exception.SeoulMetroGetStationServiceException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Deprecated
@Component
@RequiredArgsConstructor
public class SeoulMetroApiClient {
    // 공통 seoulRestClient 를 사용하는 특정 도메인의 API 리스트
    private final RestClient seoulMetroApiRestClient;

    @Value("${external-server.seoul-metro.api.base-url}")
    private String baseUrl;

    @Value("${external-server.seoul-metro.api.access-key}")
    private String apiKey;

    public MetroApiResponse getStationInfo(SeoulMetroStationApiRequest request) {
        try {
            String xmlResponse = seoulMetroApiRestClient.get()
                .uri(baseUrl+apiKey + "/xml/SearchSTNBySubwayLineInfo/"
                    + request.startPage() + "/" + request.endPage()
                    + "/%20/%20/" + request.stationName())
                .retrieve()
                .body(String.class);

            if (xmlResponse == null) {
                throw new SeoulMetroGetStationServiceException("API response is null");
            }

            // JAXB Context 생성
            JAXBContext jaxbContext;
            if (xmlResponse.contains("<RESULT>") && !xmlResponse.contains("<SearchSTNBySubwayLineInfo>")) {
                jaxbContext = JAXBContext.newInstance(SeoulMetroStationApiErrorResponse.class);
            } else {
                jaxbContext = JAXBContext.newInstance(SeoulMetroStationApiResponse.class);
            }

            // XML을 Java 객체로 변환
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (MetroApiResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
        } catch (Exception e) {
            throw new SeoulMetroGetStationServiceException(e.getMessage());
        }
    }

}

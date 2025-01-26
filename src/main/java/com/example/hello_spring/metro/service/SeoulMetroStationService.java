package com.example.hello_spring.metro.service;

import com.example.hello_spring.global.enums.ErrorCode;
import com.example.hello_spring.global.exception.ExternalInterfaceException;
import com.example.hello_spring.metro.client.SeoulMetroInterfaceClient;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiErrorResponse;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiRequest;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiResponse;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiResponse.SeoulMetroStationInfo;
import com.example.hello_spring.metro.dto.SeoulMetroStationList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeoulMetroStationService {
    private final SeoulMetroInterfaceClient seoulMetroInterfaceClient;

    public List<SeoulMetroStationList> getSeoulMetroStationByLineNumberV2(SeoulMetroStationApiRequest request) {
        try {
            String xmlResponse = seoulMetroInterfaceClient.getSeoulMetroStation(
                request.startPage(), request.endPage(), request.stationName());
            validationResponse(xmlResponse);

            if (isErrorResponse(xmlResponse)) {
                handleErrorResponse(xmlResponse);
            }

            SeoulMetroStationApiResponse response = parseSuccessResponse(xmlResponse);
            return convertToStationList(response);
        }catch (ExternalInterfaceException e){
            throw ExternalInterfaceException.of(e.getErrorCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private SeoulMetroStationApiResponse parseSuccessResponse(String xmlResponse) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SeoulMetroStationApiResponse.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (SeoulMetroStationApiResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
        } catch (Exception e) {
            throw ExternalInterfaceException.of(ErrorCode.SEOUL_METRO_RESPONSE_FAIL,
                "Failed to parse success response: " + e.getMessage()
            );
        }
    }

    private static void validationResponse(String xmlResponse) {
        if (xmlResponse == null) {
            throw ExternalInterfaceException.of(ErrorCode.SEOUL_METRO_RESPONSE_FAIL, "API response is null");
        }
    }

    private boolean isErrorResponse(String xmlResponse) {
        return xmlResponse.contains("<RESULT>") && !xmlResponse.contains("<SearchSTNBySubwayLineInfo>");
    }

    private void handleErrorResponse(String xmlResponse) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SeoulMetroStationApiErrorResponse.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                SeoulMetroStationApiErrorResponse errorResponse =
                    (SeoulMetroStationApiErrorResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));

            throw ExternalInterfaceException.of(ErrorCode.SEOUL_METRO_RESPONSE_FAIL,
                String.format("Error Code: %s, Message: %s",
                    errorResponse.getErrorCode(),
                    errorResponse.getErrorMessage())
            );
        } catch (JAXBException e) {
            throw ExternalInterfaceException.of(ErrorCode.SEOUL_METRO_RESPONSE_FAIL,
                "JAXBException error: " + e.getMessage()
            );
        }
    }

    private List<SeoulMetroStationList> convertToStationList(SeoulMetroStationApiResponse response) {
        if (response.getStationsInfoList() == null) {
            return Collections.emptyList();
        }

        return response.getStationsInfoList().stream()
            .map(this::convertToStationList)
            .collect(Collectors.toList());
    }

    private SeoulMetroStationList convertToStationList(SeoulMetroStationInfo stationInfo) {
        return new SeoulMetroStationList(
            stationInfo.getStationCd(),
            stationInfo.getStationNm(),
            stationInfo.getLineNum()
        );
    }
}

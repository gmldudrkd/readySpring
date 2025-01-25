package com.example.hello_spring.metro.service;

import com.example.hello_spring.metro.client.SeoulMetroApiClient;
import com.example.hello_spring.metro.dto.MetroApiResponse;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiErrorResponse;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiRequest;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiResponse;
import com.example.hello_spring.metro.dto.SeoulMetroStationApiResponse.SeoulMetroStationInfo;
import com.example.hello_spring.metro.dto.SeoulMetroStationList;
import com.example.hello_spring.metro.exception.SeoulMetroGetStationServiceException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeoulMetroStationService {
    private final SeoulMetroApiClient seoulMetroApiClient;

    public List<SeoulMetroStationList> getSeoulMetroStationByLineNumber(SeoulMetroStationApiRequest request) {
        try {
            MetroApiResponse stationApiResponse = seoulMetroApiClient.getStationInfo(request);
            if (stationApiResponse instanceof SeoulMetroStationApiErrorResponse errorResponse) {
                throw new SeoulMetroGetStationServiceException(
                    errorResponse.getErrorCode() + " " + errorResponse.getErrorMessage()
                );
            }

            if (stationApiResponse instanceof SeoulMetroStationApiResponse successResponse) {
                if (successResponse.getStationsInfoList() == null) {
                    return Collections.emptyList();
                }
                return convertToStationList(successResponse.getStationsInfoList());
            }
            throw new SeoulMetroGetStationServiceException("알 수 없는 응답 형식");
        }catch (Exception e){
            throw new SeoulMetroGetStationServiceException(e.getMessage());
        }
    }

    private List<SeoulMetroStationList> convertToStationList(List<SeoulMetroStationInfo> stationInfos) {
        return stationInfos.stream()
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

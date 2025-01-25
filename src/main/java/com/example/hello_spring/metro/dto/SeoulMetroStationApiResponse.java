package com.example.hello_spring.metro.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "SearchSTNBySubwayLineInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeoulMetroStationApiResponse implements MetroApiResponse {
    @XmlElement(name = "list_total_count")
    private int listTotalCount;

    @XmlElement(name = "RESULT")
    private InfoResult infoResult;

    @XmlElement(name = "row")
    private List<SeoulMetroStationInfo> stationsInfoList;

    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InfoResult {
        @XmlElement(name = "CODE")
        private String code;

        @XmlElement(name = "MESSAGE")
        private String message;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeoulMetroStationInfo {
        @XmlElement(name = "STATION_CD")
        private String stationCd;

        @XmlElement(name = "STATION_NM")
        private String stationNm;

        @XmlElement(name = "STATION_NM_ENG")
        private String stationNmEng;

        @XmlElement(name = "LINE_NUM")
        private String lineNum;

        @XmlElement(name = "FR_CODE")
        private String frCode;

        @XmlElement(name = "STATION_NM_CHN")
        private String stationNmChn;

        @XmlElement(name = "STATION_NM_JPN")
        private String stationNmJpn;
    }
}


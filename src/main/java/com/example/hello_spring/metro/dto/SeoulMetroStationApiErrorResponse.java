package com.example.hello_spring.metro.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "RESULT")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeoulMetroStationApiErrorResponse implements MetroApiResponse {
    @XmlElement(name = "CODE")
    private String errorCode;

    @XmlElement(name = "MESSAGE")
    private String errorMessage;
}

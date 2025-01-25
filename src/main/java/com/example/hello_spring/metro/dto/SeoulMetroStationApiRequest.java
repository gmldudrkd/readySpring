package com.example.hello_spring.metro.dto;


public record SeoulMetroStationApiRequest(
    int startPage,
    int endPage,
    String stationName
) {

}

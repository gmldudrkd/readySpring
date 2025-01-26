package com.example.hello_spring.metro.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange// HTTP 클라이언트 인터페이스
public interface SeoulMetroInterfaceClient {

    @GetExchange("/xml/SearchSTNBySubwayLineInfo/{startPage}/{endPage}")
    String getSeoulMetroStation(
        @PathVariable("startPage") int startPage,
        @PathVariable("endPage") int endPage,
        @PathVariable("stationLine") String stationLine
    );
}

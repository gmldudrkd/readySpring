package com.example.hello_spring.metro.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange// HTTP 클라이언트 인터페이스
public interface SeoulMetroInterfaceClient {
    //외부로 연동되는 API 목록 (사용하는 리스트와 request, response 값을 명확하게 관리할 수 있음)

    @GetExchange("/xml/SearchSTNBySubwayLineInfo/{startPage}/{endPage}")
    String getSeoulMetroStation(
        @PathVariable("startPage") int startPage,
        @PathVariable("endPage") int endPage,
        @PathVariable("stationLine") String stationLine
    );
}

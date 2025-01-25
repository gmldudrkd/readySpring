package com.example.hello_spring.metro.controller;

import com.example.hello_spring.metro.dto.SeoulMetroStationApiRequest;
import com.example.hello_spring.metro.dto.SeoulMetroStationList;
import com.example.hello_spring.metro.service.SeoulMetroStationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/metro")
@RequiredArgsConstructor
public class SeoulMetroController {
    private final SeoulMetroStationService seoulMetroStationService;

    @PostMapping(value = "/stations")
    public ResponseEntity<List<SeoulMetroStationList>> getStationsByName(
        @RequestBody SeoulMetroStationApiRequest request
    ) {
        //지하철 역 정보 조회
        List<SeoulMetroStationList> result = seoulMetroStationService
            .getSeoulMetroStationByLineNumber(request);

        return ResponseEntity.ok(result);
    }
}

package com.example.hello_spring.metro.exception;

@Deprecated
public class SeoulMetroGetStationServiceException extends RuntimeException {

    public SeoulMetroGetStationServiceException(String message) {
        super(message);
    }
}

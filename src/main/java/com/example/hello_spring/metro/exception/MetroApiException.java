package com.example.hello_spring.metro.exception;

public class MetroApiException extends RuntimeException {

    public MetroApiException(String message) {
        super("Metro Api Exception : "+message);
    }
}

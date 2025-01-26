package com.example.hello_spring.global.exception;

import com.example.hello_spring.global.enums.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExternalInterfaceException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    protected ExternalInterfaceException(ErrorCode errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    protected ExternalInterfaceException(ErrorCode errorCode, String errorMessage) {
        this(errorCode, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ExternalInterfaceException of(ErrorCode errorCode, String errorMessage, HttpStatus httpStatus) {
        return new ExternalInterfaceException(errorCode, errorMessage, httpStatus);
    }

    public static ExternalInterfaceException of(ErrorCode errorCode, String errorMessage) {
        return new ExternalInterfaceException(errorCode, errorMessage);
    }
}

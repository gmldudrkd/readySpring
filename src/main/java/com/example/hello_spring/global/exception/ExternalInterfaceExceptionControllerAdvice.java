package com.example.hello_spring.global.exception;

import com.example.hello_spring.global.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExternalInterfaceExceptionControllerAdvice {

    @ExceptionHandler(ExternalInterfaceException.class)
    public ResponseEntity<ErrorResponseDto> handleWmsException(ExternalInterfaceException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            e.getMessage(),
            e.getErrorCode()
        );

        return ResponseEntity
            .status(e.getHttpStatus())
            .body(errorResponseDto);
    }
}

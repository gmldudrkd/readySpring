package com.example.hello_spring.global.dto;

import com.example.hello_spring.global.enums.ErrorCode;

public record ErrorResponseDto(
    String errorMessage,
    ErrorCode errorCode
) {
}

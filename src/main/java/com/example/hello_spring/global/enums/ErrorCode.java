package com.example.hello_spring.global.enums;

public enum ErrorCode {
    BAD_REQUEST("Bad Request"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    NOT_FOUND("Not Found"),
    METHOD_NOT_ALLOWED("Method Not Allowed"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    MEMBER_NOT_FOUND("Member Not Found"),
    UNSUPPORTED_HTTP_METHOD("Unsupported HTTP Method"),
    SEOUL_METRO_API_CALL_FAIL("Failed to communicate with the SEOUL METRO system."),
    SEOUL_METRO_RESPONSE_FAIL("Not collect SEOUL METRO response"),
    ;

    public final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}

package com.woowacourse.auth.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String message;

    private ErrorResponse() {
    }

    private ErrorResponse(final String message) {
        this.message = message;
    }

    public static ErrorResponse from(final RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}

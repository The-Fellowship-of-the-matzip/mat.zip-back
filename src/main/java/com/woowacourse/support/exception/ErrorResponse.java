package com.woowacourse.support.exception;

import lombok.Getter;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public class ErrorResponse {

    private static final int VALID_ERROR_FIRST_INDEX = 0;

    private String message;

    private ErrorResponse() {
    }

    private ErrorResponse(final String message) {
        this.message = message;
    }

    public static ErrorResponse from(final RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    public static ErrorResponse from(final MethodArgumentNotValidException exception) {
        return new ErrorResponse(exception.getFieldErrors()
                .get(VALID_ERROR_FIRST_INDEX)
                .getDefaultMessage());
    }

    public static ErrorResponse from(final String message) {
        return new ErrorResponse(message);
    }
}

package com.woowacourse.auth.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String message;

    private ErrorResponse() {
    }

    public ErrorResponse(final String message) {
        this.message = message;
    }
}

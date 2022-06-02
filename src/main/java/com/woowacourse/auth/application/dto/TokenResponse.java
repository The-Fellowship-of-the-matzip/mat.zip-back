package com.woowacourse.auth.application.dto;

import lombok.Getter;

@Getter
public class TokenResponse {

    private String accessToken;

    private TokenResponse() {
    }

    public TokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}

package com.woowacourse.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    private GithubTokenResponse() {
    }

    public GithubTokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}

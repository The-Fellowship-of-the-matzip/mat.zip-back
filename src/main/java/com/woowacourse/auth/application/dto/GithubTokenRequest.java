package com.woowacourse.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubTokenRequest {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    private String code;

    private GithubTokenRequest() {
    }

    public GithubTokenRequest(final String clientId, final String clientSecret, final String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
    }
}

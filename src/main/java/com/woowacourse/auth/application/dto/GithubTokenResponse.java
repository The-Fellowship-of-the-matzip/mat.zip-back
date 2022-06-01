package com.woowacourse.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GithubTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    public GithubTokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}

package com.woowacourse.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GithubProfileResponse {

    @JsonProperty("id")
    private String githubId;

    @JsonProperty("login")
    private String name;

    @JsonProperty("avatar_url")
    private String image;

    public GithubProfileResponse(final String githubId, final String name, final String image) {
        this.githubId = githubId;
        this.name = name;
        this.image = image;
    }
}

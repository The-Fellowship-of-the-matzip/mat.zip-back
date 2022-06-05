package com.woowacourse.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.matzip.domain.member.Member;
import lombok.Getter;

@Getter
public class GithubProfileResponse {

    @JsonProperty("id")
    private String githubId;

    @JsonProperty("login")
    private String username;

    @JsonProperty("avatar_url")
    private String profileImage;

    private GithubProfileResponse() {
    }

    public GithubProfileResponse(final String githubId, final String username, final String profileImage) {
        this.githubId = githubId;
        this.username = username;
        this.profileImage = profileImage;
    }

    public Member toMember() {
        return Member.builder()
                .githubId(githubId)
                .username(username)
                .profileImage(profileImage)
                .build();
    }
}

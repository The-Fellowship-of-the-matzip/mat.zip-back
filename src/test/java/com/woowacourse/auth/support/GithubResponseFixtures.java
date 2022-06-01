package com.woowacourse.auth.support;

import java.util.Arrays;

public enum GithubResponseFixtures {

    HUNI("1", "access_token_1", "87312401",
            "jayjaehunchoi", "https://avatars.githubusercontent.com/u/87312401?v=4");

    private final String code;
    private final String accessToken;
    private final String githubId;
    private final String username;
    private final String profileImage;

    GithubResponseFixtures(String code, String accessToken, String githubId, String username,
                           String profileImage) {
        this.code = code;
        this.accessToken = accessToken;
        this.githubId = githubId;
        this.username = username;
        this.profileImage = profileImage;
    }

    public static GithubResponseFixtures findResponseByCode(final String code) {
        return Arrays.stream(values())
                .filter(value -> code.equals(value.code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static GithubResponseFixtures findResponseByAccessToken(final String accessToken) {
        return Arrays.stream(values())
                .filter(value -> accessToken.equals(value.accessToken))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getCode() {
        return code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getGithubId() {
        return githubId;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImage() {
        return profileImage;
    }
}

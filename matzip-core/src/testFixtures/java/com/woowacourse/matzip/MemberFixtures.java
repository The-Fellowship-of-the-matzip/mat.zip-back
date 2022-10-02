package com.woowacourse.matzip;

import com.woowacourse.matzip.domain.member.Member;

public enum MemberFixtures {

    HUNI("1", "access_token_1", "87312401",
            "jayjaehunchoi", "https://avatars.githubusercontent.com/u/87312401?v=4"),
    ORI("2", "access_token_2", "69106910",
            "jinyoungchoi95", "https://avatars.githubusercontent.com/u/69106910?v=4");

    private final String code;
    private final String accessToken;
    private final String githubId;
    private final String username;
    private final String profileImage;

    MemberFixtures(final String code, final String accessToken, final String githubId, final String username,
                   final String profileImage) {
        this.code = code;
        this.accessToken = accessToken;
        this.githubId = githubId;
        this.username = username;
        this.profileImage = profileImage;
    }

    public Member toMember() {
        return Member.builder()
                .githubId(getGithubId())
                .username(getUsername())
                .profileImage(getProfileImage())
                .build();
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

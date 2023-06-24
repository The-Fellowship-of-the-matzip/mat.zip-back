package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.member.Member;
import lombok.Getter;

@Getter
public class ProfileResponse {

    private String username;
    private String profileImage;
    private Integer reviewCount;
    private Double ratingAverage;

    private ProfileResponse() {
    }

    public ProfileResponse(final String username, final String profileImage, final Integer reviewCount,
                           final Double ratingAverage) {
        this.username = username;
        this.profileImage = profileImage;
        this.reviewCount = reviewCount;
        this.ratingAverage = ratingAverage;
    }

    public static ProfileResponse of(final Member member, final Integer reviewCount, final Double ratingAverage) {
        return new ProfileResponse(member.getUsername(), member.getProfileImage(), reviewCount, ratingAverage);
    }
}

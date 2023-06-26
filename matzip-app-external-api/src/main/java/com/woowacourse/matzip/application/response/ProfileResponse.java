package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.member.Member;
import lombok.Getter;

@Getter
public class ProfileResponse {

    private String username;
    private String profileImage;
    private Long reviewCount;
    private Double averageRating;

    private ProfileResponse() {
    }

    public ProfileResponse(final String username, final String profileImage, final Long reviewCount,
                           final Double averageRating) {
        this.username = username;
        this.profileImage = profileImage;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
    }

    public static ProfileResponse of(final Member member, final Long reviewCount, final Double ratingAverage) {
        return new ProfileResponse(member.getUsername(), member.getProfileImage(), reviewCount, ratingAverage);
    }
}

package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.review.Review;
import lombok.Getter;

@Getter
public class ReviewResponse {

    private Long id;
    private ReviewAuthor author;
    private String content;
    private int rating;
    private String menu;
    private boolean updatable;

    private ReviewResponse() {
    }

    public ReviewResponse(final Long id,
                          final ReviewAuthor author,
                          final String content,
                          final int rating,
                          final String menu,
                          final boolean updatable) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.rating = rating;
        this.menu = menu;
        this.updatable = updatable;
    }

    public static ReviewResponse of(final Review review, final boolean updatable, final Long reviewCount, final Double averageRating) {
        return new ReviewResponse(
                review.getId(),
                ReviewAuthor.of(review.getMember(), reviewCount, averageRating),
                review.getContent(),
                review.getRating(),
                review.getMenu(),
                updatable
        );
    }

    @Getter
    public static class ReviewAuthor {

        private String username;
        private String profileImage;
        private Long reviewCount;
        private Double averageRating;

        private ReviewAuthor() {
        }

        public ReviewAuthor(final String username, final String profileImage, final Long reviewCount, final Double averageRating) {
            this.username = username;
            this.profileImage = profileImage;
            this.reviewCount = reviewCount;
            this.averageRating = averageRating;
        }

        public static ReviewAuthor of(final Member member, final Long reviewCount, final Double averageRating) {
            return new ReviewAuthor(member.getUsername(), member.getProfileImage(), reviewCount, averageRating);
        }
    }
}

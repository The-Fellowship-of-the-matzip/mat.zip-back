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
    private String imageUrl;
    private boolean updatable;

    private ReviewResponse() {
    }

    public ReviewResponse(final Long id,
                          final ReviewAuthor author,
                          final String content,
                          final int rating,
                          final String menu,
                          final String imageUrl,
                          final boolean updatable) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.rating = rating;
        this.menu = menu;
        this.imageUrl = imageUrl;
        this.updatable = updatable;
    }

    public static ReviewResponse of(final Review review, final boolean updatable) {
        return new ReviewResponse(
                review.getId(),
                ReviewAuthor.from(review.getMember()),
                review.getContent(),
                review.getRating(),
                review.getMenu(),
                review.getImageUrl(),
                updatable
        );
    }

    @Getter
    public static class ReviewAuthor {

        private String username;
        private String profileImage;

        private ReviewAuthor() {
        }

        public ReviewAuthor(final String username, final String profileImage) {
            this.username = username;
            this.profileImage = profileImage;
        }

        public static ReviewAuthor from(final Member member) {
            return new ReviewAuthor(member.getUsername(), member.getProfileImage());
        }
    }
}

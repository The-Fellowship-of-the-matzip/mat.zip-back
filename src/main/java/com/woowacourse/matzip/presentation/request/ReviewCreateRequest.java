package com.woowacourse.matzip.presentation.request;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.review.Review;

public class ReviewCreateRequest {

    private String content;
    private int rating;
    private String menu;

    private ReviewCreateRequest() {
    }

    public ReviewCreateRequest(final String content, final int rating, final String menu) {
        this.content = content;
        this.rating = rating;
        this.menu = menu;
    }

    public Review toReviewWithMemberAndRestaurantId(final Member member, final Long restaurantId) {
        return Review.builder()
                .member(member)
                .restaurantId(restaurantId)
                .content(content)
                .rating(rating)
                .menu(menu)
                .build();
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public String getMenu() {
        return menu;
    }
}

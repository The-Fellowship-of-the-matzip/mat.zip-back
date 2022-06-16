package com.woowacourse.matzip.presentation.request;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.review.Review;

public class ReviewCreateRequest {

    private String content;
    private int score;
    private String menu;

    private ReviewCreateRequest() {
    }

    public ReviewCreateRequest(final String content, final int score, final String menu) {
        this.content = content;
        this.score = score;
        this.menu = menu;
    }

    public Review toReviewWithMemberAndRestaurantId(final Member member, final Long restaurantId) {
        return Review.builder()
                .member(member)
                .restaurantId(restaurantId)
                .content(content)
                .score(score)
                .menu(menu)
                .build();
    }

    public String getContent() {
        return content;
    }

    public int getScore() {
        return score;
    }

    public String getMenu() {
        return menu;
    }
}

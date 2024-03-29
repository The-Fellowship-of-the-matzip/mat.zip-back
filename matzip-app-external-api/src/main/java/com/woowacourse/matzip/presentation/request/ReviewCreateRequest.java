package com.woowacourse.matzip.presentation.request;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.review.Review;
import jakarta.validation.constraints.NotNull;

public class ReviewCreateRequest {

    @NotNull(message = "리뷰 내용은 null이 들어올 수 없습니다.")
    private String content;
    private int rating;
    @NotNull(message = "리뷰 메뉴는 null이 들어올 수 없습니다.")
    private String menu;
    private String imageUrl;

    private ReviewCreateRequest() {
    }

    public ReviewCreateRequest(final String content, final int rating, final String menu, final String imageUrl) {
        this.content = content;
        this.rating = rating;
        this.menu = menu;
        this.imageUrl = imageUrl;
    }

    public Review toReviewWithMemberAndRestaurantId(final Member member, final Long restaurantId) {
        return Review.builder()
                .member(member)
                .restaurantId(restaurantId)
                .content(content)
                .rating(rating)
                .menu(menu)
                .imageUrl(imageUrl)
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

    public String getImageUrl() {
        return imageUrl;
    }
}

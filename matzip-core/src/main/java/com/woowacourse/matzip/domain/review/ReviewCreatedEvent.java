package com.woowacourse.matzip.domain.review;

import lombok.Getter;

@Getter
public class ReviewCreatedEvent {

    private final Long restaurantId;
    private final int rating;
    private final String imageUrl;

    public ReviewCreatedEvent(final Long restaurantId, final int rating, final String imageUrl) {
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }
}

package com.woowacourse.matzip.domain.review;

import lombok.Getter;

@Getter
public class ReviewCreatedEvent {

    private final Long restaurantId;
    private final int rating;

    public ReviewCreatedEvent(final Long restaurantId, final int rating) {
        this.restaurantId = restaurantId;
        this.rating = rating;
    }
}

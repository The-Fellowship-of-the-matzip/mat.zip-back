package com.woowacourse.matzip.domain.review;

import lombok.Getter;

@Getter
public class ReviewCreateEvent {

    private final Long restaurantId;
    private final int rating;

    public ReviewCreateEvent(final Long restaurantId, final int rating) {
        this.restaurantId = restaurantId;
        this.rating = rating;
    }
}

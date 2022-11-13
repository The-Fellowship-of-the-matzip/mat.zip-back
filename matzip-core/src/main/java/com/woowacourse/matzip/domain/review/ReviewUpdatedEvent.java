package com.woowacourse.matzip.domain.review;

import lombok.Getter;

@Getter
public class ReviewUpdatedEvent {

    private final Long restaurantId;
    private final int ratingGap;

    public ReviewUpdatedEvent(final Long restaurantId, final int ratingGap) {
        this.restaurantId = restaurantId;
        this.ratingGap = ratingGap;
    }
}

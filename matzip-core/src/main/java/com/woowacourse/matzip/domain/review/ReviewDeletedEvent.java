package com.woowacourse.matzip.domain.review;

import lombok.Getter;

@Getter
public class ReviewDeletedEvent {

    private final Long restaurantId;
    private final int rating;
    private final String imageUrl;

    public ReviewDeletedEvent(final Long restaurantId, final int rating, String imageUrl) {
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }
}

package com.woowacourse.matzip.application.response;

import java.util.List;
import lombok.Getter;

@Getter
public class ReviewsResponse {

    private boolean hasNext;
    private List<ReviewResponse> reviews;

    private ReviewsResponse() {
    }

    public ReviewsResponse(boolean hasNext, List<ReviewResponse> reviews) {
        this.hasNext = hasNext;
        this.reviews = reviews;
    }
}

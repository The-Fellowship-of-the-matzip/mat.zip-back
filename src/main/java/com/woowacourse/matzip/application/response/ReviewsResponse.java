package com.woowacourse.matzip.application.response;

import java.util.List;

public class ReviewsResponse {

    private boolean hasNext;
    private List<ReviewResponse> reviews;

    private ReviewsResponse() {
    }

    public ReviewsResponse(boolean hasNext, List<ReviewResponse> reviews) {
        this.hasNext = hasNext;
        this.reviews = reviews;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public List<ReviewResponse> getReviews() {
        return reviews;
    }
}

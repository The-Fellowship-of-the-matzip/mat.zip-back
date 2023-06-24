package com.woowacourse.matzip.application.response;

import java.util.List;
import lombok.Getter;

@Getter
public class MyReviewsResponse {

    private boolean hasNext;
    private List<MyReviewResponse> reviews;

    private MyReviewsResponse() {
    }

    public MyReviewsResponse(final boolean hasNext, final List<MyReviewResponse> reviews) {
        this.hasNext = hasNext;
        this.reviews = reviews;
    }
}

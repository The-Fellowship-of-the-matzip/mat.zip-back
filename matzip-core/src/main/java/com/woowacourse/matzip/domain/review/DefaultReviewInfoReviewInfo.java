package com.woowacourse.matzip.domain.review;

public class DefaultReviewInfoReviewInfo implements MemberReviewInfo {
    @Override
    public Long getMemberId() {
        return null;
    }

    @Override
    public Long getReviewCount() {
        return 0L;
    }

    @Override
    public Double getAverageRating() {
        return 0d;
    }
}

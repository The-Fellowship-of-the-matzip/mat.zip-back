package com.woowacourse.matzip.domain.review;

public class DefaultReviewInfo implements MemberReviewInfo {

    private static final DefaultReviewInfo DEFAULT_REVIEW_INFO = new DefaultReviewInfo();

    private DefaultReviewInfo() {
    }

    public static DefaultReviewInfo getInstance() {
        return DEFAULT_REVIEW_INFO;
    }

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

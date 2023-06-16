package com.woowacourse.matzip.domain.review;

public interface ReviewInfoByMember {
    Long getMemberId();
    Long getReviewCount();
    Double getAverageRating();
}

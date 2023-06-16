package com.woowacourse.matzip.domain.review;

import java.util.List;

public class ReviewInfoByMembers {
    private final List<ReviewInfoByMember> reviewInfoByMembers;

    public ReviewInfoByMembers(final List<ReviewInfoByMember> reviewInfoByMembers) {
        this.reviewInfoByMembers = reviewInfoByMembers;
    }

    public ReviewInfoByMember findById(final Long id) {
        return reviewInfoByMembers.stream()
                .filter(reviewInfoByMember -> reviewInfoByMember.getMemberId().equals(id))
                .findAny()
                .orElse(new DefaultReviewInfo());
    }
}

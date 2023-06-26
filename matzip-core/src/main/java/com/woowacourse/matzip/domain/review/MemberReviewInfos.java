package com.woowacourse.matzip.domain.review;

import java.util.List;

public class MemberReviewInfos {
    private final List<MemberReviewInfo> memberReviewInfos;

    public MemberReviewInfos(final List<MemberReviewInfo> memberReviewInfos) {
        this.memberReviewInfos = memberReviewInfos;
    }

    public MemberReviewInfo findByMemberId(final Long memberId) {
        return memberReviewInfos.stream()
                .filter(reviewInfoByMember -> reviewInfoByMember.getMemberId().equals(memberId))
                .findAny()
                .orElse(new DefaultReviewInfoReviewInfo());
    }
}

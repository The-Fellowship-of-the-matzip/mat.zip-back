package com.woowacourse.matzip.domain.review;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReviewCountByMemberIdsDto {
    private List<ReviewCountByMemberIdDto> reviewCountByMemberIdsDto;

    public ReviewCountByMemberIdsDto(final List<ReviewCountByMemberIdDto> reviewCountByMemberIdsDto) {
        this.reviewCountByMemberIdsDto = reviewCountByMemberIdsDto;
    }

    public Map<Long, Long> toMap() {
        return reviewCountByMemberIdsDto.stream()
                .collect(Collectors.toMap(ReviewCountByMemberIdDto::getMemberId, ReviewCountByMemberIdDto::getReviewCount));
    }
}

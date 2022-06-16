package com.woowacourse.matzip.domain.review;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.exception.InvalidReviewException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ReviewTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 6})
    void 별점_범위제한인_경우_예외발생(final int score) {
        assertThatThrownBy(() -> Review.builder().rating(score).build())
                .isInstanceOf(InvalidReviewException.class)
                .hasMessage("리뷰 점수는 0점부터 5점까지만 가능합니다.");
    }
}

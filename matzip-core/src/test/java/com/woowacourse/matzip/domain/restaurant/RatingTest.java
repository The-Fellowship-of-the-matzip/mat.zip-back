package com.woowacourse.matzip.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RatingTest {

    @Test
    void 평점평균을_생성한다() {
        int count = 3;
        long sum = 13L;
        float expected = 4.33f;
        Rating actual = Rating.builder()
                .count(count)
                .sum(sum)
                .build();
        assertThat(actual.getAverage()).isEqualTo(expected);
    }
}

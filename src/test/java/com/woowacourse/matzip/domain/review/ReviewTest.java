package com.woowacourse.matzip.domain.review;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.exception.InvalidLengthException;
import com.woowacourse.matzip.exception.InvalidReviewException;
import org.junit.jupiter.api.Test;
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

    @Test
    void 리뷰_생성_시_메뉴_이름_길이_제한() {
        String menu = "리뷰의 메뉴 이름이 이렇게 길 수 없습니다.";

        assertThatThrownBy(() -> Review.builder()
                .menu(menu)
                .content("리뷰내용")
                .build())
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage("메뉴의 이름은(는) 길이가 20 이하의 값만 입력할 수 있습니다.");
    }
}

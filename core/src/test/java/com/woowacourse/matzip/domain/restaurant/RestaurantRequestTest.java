package com.woowacourse.matzip.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.exception.InvalidLengthException;
import org.junit.jupiter.api.Test;

class RestaurantRequestTest {

    @Test
    void 식당_추가_요청_생성_시_이름_길이_제한() {
        String name = "식당의 이름이 이렇게 길 수 없습니다.";

        assertThatThrownBy(() -> RestaurantRequest.builder()
                .name(name)
                .build())
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage("식당 이름은(는) 길이가 20 이하의 값만 입력할 수 있습니다.");
    }
}

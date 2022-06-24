package com.woowacourse.matzip.support;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.exception.InvalidLengthException;
import org.junit.jupiter.api.Test;

public class LengthValidatorTest {

    @Test
    void 값이_최대길이보다_긴_경우_예외발생() {
        String value = "리뷰의 길이를 굉장히 길게 작성하겠습니다.";
        String name = "리뷰";
        int max = 5;

        assertThatThrownBy(() -> LengthValidator.checkStringLength(value, name, max))
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage(name + "는 " + max + "보다 작은 값만 입력할 수 있습니다.");
    }
}

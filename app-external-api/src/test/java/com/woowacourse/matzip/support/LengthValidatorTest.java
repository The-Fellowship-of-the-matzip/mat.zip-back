package com.woowacourse.matzip.support;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.exception.InvalidLengthException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LengthValidatorTest {

    @Test
    void 값이_최대길이보다_긴_경우_예외발생() {
        String value = "리뷰의 길이를 굉장히 길게 작성하겠습니다.";
        String name = "리뷰";
        int max = 5;

        assertThatThrownBy(() -> LengthValidator.checkStringLength(value, max, name))
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage(name + "은(는) 길이가 " + max + " 이하의 값만 입력할 수 있습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "리뷰의 길이를 굉장히 길게 작성하겠습니다."})
    void 값이_범위에_없는_경우_예외발생(final String value) {
        int max = 5;
        int min = 1;
        String name = "리뷰";

        assertThatThrownBy(() -> LengthValidator.checkStringLength(value, max, min, name))
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage(name + "은(는) 길이가 " + min + "와 " + max + " 사이 값만 입력할 수 있습니다.");
    }
}

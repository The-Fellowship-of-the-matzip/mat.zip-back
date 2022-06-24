package com.woowacourse.matzip.domain.campus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.exception.InvalidLengthException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CampusTest {

    @ParameterizedTest
    @CsvSource(value = {"1,true", "2,false"})
    void 캠퍼스_이름이_동일한지_확인한다(final Long id, final boolean expected) {
        Campus campus = new Campus(1L, "JAMSIL");

        assertThat(campus.isSameId(id)).isEqualTo(expected);
    }

    @Test
    void 캠퍼스_생성_시_이름_길이_제한() {
        String name = "캠퍼스의 이름이 이렇게 길 수 없습니다.";

        assertThatThrownBy(() -> Campus.builder().name(name).build())
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage("캠퍼스 이름은(는) 20보다 작은 값만 입력할 수 있습니다.");
    }
}

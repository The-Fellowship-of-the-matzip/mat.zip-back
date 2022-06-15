package com.woowacourse.matzip.domain.campus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CampusTest {

    @Test
    void 캠퍼스_생성() {
        assertThat(new Campus(1L, "JAMSIL")).isInstanceOf(Campus.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,true", "2,false"})
    void 캠퍼스_이름이_동일한지_확인한다(final Long id, final boolean expected) {
        Campus campus = new Campus(1L, "JAMSIL");

        assertThat(campus.isSameId(id)).isEqualTo(expected);
    }
}

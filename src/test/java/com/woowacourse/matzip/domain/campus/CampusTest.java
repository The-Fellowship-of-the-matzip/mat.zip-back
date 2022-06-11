package com.woowacourse.matzip.domain.campus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampusTest {

    @Test
    void 캠퍼스_생성() {
        assertThat(new Campus("JAMSIL")).isInstanceOf(Campus.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"JAMSIL,true", "SEOLLEUNG,false"})
    void 캠퍼스_이름이_동일한지_확인한다(String name, boolean expected) {
        Campus campus = new Campus("JAMSIL");

        assertThat(campus.isSameCampusName(name)).isEqualTo(expected);
    }
}

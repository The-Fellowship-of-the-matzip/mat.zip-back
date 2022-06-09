package com.woowacourse.matzip.domain.campus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CampusTest {

    @Test
    void 캠퍼스_생성() {
        assertThat(new Campus(CampusName.JAMSIL)).isInstanceOf(Campus.class);
    }
}

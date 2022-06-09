package com.woowacourse.matzip.domain.campus;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CampusNameTest {

    @Test
    void 존재하지_않는_캠퍼스_이름() {
        assertThatThrownBy(() -> CampusName.from("강남"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 캠퍼스 이름입니다.");
    }
}

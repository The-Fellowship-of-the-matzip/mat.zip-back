package com.woowacourse.auth.presentation.interceptor;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class RequestPathPatternTest {

    @Test
    void null_일경우_생성할_수_없다() {
        assertThatThrownBy(() -> new RequestPathPattern(null, null))
                .isInstanceOf(NullPointerException.class);
    }
}

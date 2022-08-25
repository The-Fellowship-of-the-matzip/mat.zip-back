package com.woowacourse.auth.presentation.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

class RequestPathPatternTest {

    @Test
    void null_일경우_생성할_수_없다() {
        assertThatThrownBy(() -> new RequestPathPattern(null, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void request가_ant_match_하다() {
        RequestPathPattern requestPathPattern = new RequestPathPattern("/api/**", PathMethod.POST);
        assertThat(requestPathPattern.matches(new AntPathMatcher(), "/api/reviews", "POST")).isTrue();
    }
}

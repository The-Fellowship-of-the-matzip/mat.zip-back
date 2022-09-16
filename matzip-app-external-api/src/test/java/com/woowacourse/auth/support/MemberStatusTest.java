package com.woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MemberStatusTest {

    @Test
    void 익명이다() {
        assertThat(MemberStatus.ANONYMOUS.isAnonymous()).isTrue();
    }
}

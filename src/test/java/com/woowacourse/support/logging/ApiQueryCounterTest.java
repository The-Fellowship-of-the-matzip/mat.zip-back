package com.woowacourse.support.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ApiQueryCounterTest {

    @Test
    void 쿼리_개수를_증가시킨다() {
        ApiQueryCounter apiQueryCounter = new ApiQueryCounter();

        apiQueryCounter.increaseCount();

        assertThat(apiQueryCounter.getCount()).isOne();
    }
}

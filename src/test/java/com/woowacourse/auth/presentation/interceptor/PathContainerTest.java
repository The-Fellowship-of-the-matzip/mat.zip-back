package com.woowacourse.auth.presentation.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PathContainerTest {

    @Test
    void include에_포함_되지_않은_경로() {
        PathContainer pathContainer = new PathContainer();
        pathContainer.includePathPattern("/api/reviews", PathMethod.DELETE);
        assertThat(pathContainer.isNotIncludedPath("/api/reviews", "PUT")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE", "HEAD", "PATCH", "OPTIONS"})
    void include에_포함된_경로(String method) {
        PathContainer pathContainer = new PathContainer();
        pathContainer.includePathPattern("/api/reviews", PathMethod.ANY);
        assertThat(pathContainer.isNotIncludedPath("/api/reviews", method)).isFalse();
    }

    @Test
    void exclude에_포함된_경로() {
        PathContainer pathContainer = new PathContainer();
        pathContainer.includePathPattern("/api/reviews", PathMethod.ANY);
        pathContainer.excludePathPattern("/api/reviews", PathMethod.GET);
        assertThat(pathContainer.isNotIncludedPath("/api/reviews", "GET")).isTrue();
    }
}

package com.woowacourse.auth.presentation.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PathContainerTest {

    @Test
    void include에_포함_되지_않은_경로() {
        PathContainer pathContainer = new PathContainer();
        pathContainer.includePathPattern("/api/reviews", PathMethod.DELETE);
        assertThat(pathContainer.notIncludedPath("/api/reviews", "PUT")).isTrue();
    }

    @Test
    void exclude에_포함된_경로() {
        PathContainer pathContainer = new PathContainer();
        pathContainer.includePathPattern("/api/reviews", PathMethod.ANY);
        pathContainer.excludePathPattern("/api/reviews", PathMethod.GET);
        assertThat(pathContainer.notIncludedPath("/api/reviews", "GET")).isTrue();
    }
}

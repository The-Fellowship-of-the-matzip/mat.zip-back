package com.woowacourse.auth.presentation.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PathMethodTest {

    @ParameterizedTest
    @CsvSource(value = {"GET:GET", "POST:POST", "PUT:PUT", "PATCH:PATCH", "DELETE:DELETE", "OPTIONS:OPTIONS",
            "TRACE:TRACE", "HEAD:HEAD"}, delimiter = ':')
    void 대문자가_일치한다(String method, PathMethod pathMethod) {
        assertThat(pathMethod.matches(method)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"get:GET", "post:POST", "put:PUT", "patch:PATCH", "delete:DELETE", "options:OPTIONS",
            "trace:TRACE", "head:HEAD"}, delimiter = ':')
    void 소문자가_일치한다(String method, PathMethod pathMethod) {
        assertThat(pathMethod.matches(method)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"get", "GET", "post", "POST", "patch", "PATCH", "delete", "DELETE", "options", "OPTIONS",
            "trace", "TRACE", "head", "HEAD"})
    void ANY_와_일치한다(String method) {
        assertThat(PathMethod.ANY.matches(method)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"any", "ANY", "GOT", "PAST"})
    void ANY_와_일치하지_않는다(String method) {
        assertThat(PathMethod.ANY.matches(method)).isFalse();
    }
}

package com.woowacourse.auth.presentation.interceptor;

import java.util.Arrays;

public enum PathMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE,
    HEAD,
    ANY {
        @Override
        public boolean matches(final String method) {
            return Arrays.stream(values())
                    .filter(value -> value != ANY)
                    .anyMatch(value -> value.name().equalsIgnoreCase(method));
        }
    };

    public boolean matches(final String method) {
        return name().equalsIgnoreCase(method);
    }
}

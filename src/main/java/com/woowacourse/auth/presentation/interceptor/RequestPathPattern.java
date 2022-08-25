package com.woowacourse.auth.presentation.interceptor;

import java.util.Objects;
import lombok.Getter;
import org.springframework.util.PathMatcher;

@Getter
public class RequestPathPattern {

    private final String path;
    private final PathMethod method;

    public RequestPathPattern(final String path, final PathMethod method) {
        this.path = Objects.requireNonNull(path);
        this.method = Objects.requireNonNull(method);
    }

    public boolean matches(final PathMatcher pathMatcher, final String targetPath, final String pathMethod) {
        return pathMatcher.match(path, targetPath) && matchesMethod(pathMethod);
    }

    private boolean matchesMethod(final String pathMethod) {
        return method.matches(pathMethod);
    }
}

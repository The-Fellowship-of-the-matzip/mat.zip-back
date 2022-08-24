package com.woowacourse.auth.presentation.interceptor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class PathContainer {

    private final PathMatcher pathMatcher;
    private final List<RequestPathPattern> includePathPatterns;
    private final List<RequestPathPattern> excludePathPatterns;

    public PathContainer() {
        this.pathMatcher = new AntPathMatcher();
        this.includePathPatterns = new ArrayList<>();
        this.excludePathPatterns = new ArrayList<>();
    }

    public boolean notIncludedPath(final String targetPath, final String pathMethod) {
        boolean excludePattern = excludePathPatterns.stream()
                .anyMatch(requestPath -> anyMatchPathPattern(targetPath, pathMethod, requestPath));

        boolean includePattern = includePathPatterns.stream()
                .anyMatch(requestPath -> anyMatchPathPattern(targetPath, pathMethod, requestPath));

        return excludePattern || !includePattern;
    }

    private boolean anyMatchPathPattern(final String targetPath, final String pathMethod, final RequestPathPattern requestPath) {
        return pathMatcher.match(requestPath.getPath(), targetPath) &&
                requestPath.matchesMethod(pathMethod);
    }

    public void includePathPattern(final String targetPath, final PathMethod pathMethod) {
        this.includePathPatterns.add(new RequestPathPattern(targetPath, pathMethod));
    }

    public void excludePathPattern(String targetPath, PathMethod pathMethod) {
        this.excludePathPatterns.add(new RequestPathPattern(targetPath, pathMethod));
    }
}

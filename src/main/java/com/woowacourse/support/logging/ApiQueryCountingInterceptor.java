package com.woowacourse.support.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class ApiQueryCountingInterceptor implements HandlerInterceptor {

    private static final String QUERY_COUNT_LOG_FORMAT = "{} {}, QUERY_COUNT: {}";

    private final ApiQueryCounter apiQueryCounter;

    public ApiQueryCountingInterceptor(final ApiQueryCounter apiQueryCounter) {
        this.apiQueryCounter = apiQueryCounter;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        int count = apiQueryCounter.getCount();
        log.info(QUERY_COUNT_LOG_FORMAT, method, requestURI, count);
    }
}

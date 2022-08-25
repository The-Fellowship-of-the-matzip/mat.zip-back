package com.woowacourse.auth.presentation.interceptor;

import com.woowacourse.auth.support.AuthorizationExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class NotLoginInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor handlerInterceptor;

    public NotLoginInterceptor(final HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (AuthorizationExtractor.extract(request).isEmpty()) {
            return true;
        }
        return handlerInterceptor.preHandle(request, response, handler);
    }
}

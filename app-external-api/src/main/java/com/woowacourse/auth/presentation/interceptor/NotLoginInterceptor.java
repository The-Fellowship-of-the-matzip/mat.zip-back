package com.woowacourse.auth.presentation.interceptor;

import com.woowacourse.auth.presentation.AuthenticationContext;
import com.woowacourse.auth.support.AuthorizationExtractor;
import com.woowacourse.auth.support.MemberStatus;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class NotLoginInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor handlerInterceptor;
    private final AuthenticationContext authenticationContext;

    public NotLoginInterceptor(final HandlerInterceptor handlerInterceptor,
                               final AuthenticationContext authenticationContext) {
        this.handlerInterceptor = handlerInterceptor;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (AuthorizationExtractor.extract(request).isEmpty()) {
            authenticationContext.setNotLogin();
            return true;
        }
        return handlerInterceptor.preHandle(request, response, handler);
    }
}

package com.woowacourse.auth.presentation.interceptor;

import com.woowacourse.auth.presentation.AuthenticationContext;
import com.woowacourse.auth.support.AuthorizationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckerInterceptor implements HandlerInterceptor {

    private final LoginInterceptor loginInterceptor;
    private final AuthenticationContext authenticationContext;

    public LoginCheckerInterceptor(final LoginInterceptor loginInterceptor,
                                   final AuthenticationContext authenticationContext) {
        this.loginInterceptor = loginInterceptor;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (AuthorizationExtractor.extract(request).isEmpty()) {
            authenticationContext.setNotLogin();
            return true;
        }
        return loginInterceptor.preHandle(request, response, handler);
    }
}

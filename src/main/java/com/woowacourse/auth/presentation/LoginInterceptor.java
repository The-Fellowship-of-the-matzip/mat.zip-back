package com.woowacourse.auth.presentation;

import com.woowacourse.auth.application.JwtTokenProvider;
import com.woowacourse.auth.exception.InvalidTokenException;
import com.woowacourse.auth.exception.TokenNotFoundException;
import com.woowacourse.auth.support.AuthorizationExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    public LoginInterceptor(final JwtTokenProvider jwtTokenProvider,
                            final AuthenticationContext authenticationContext) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (isPreflight(request)) {
            return true;
        }
        final String token = AuthorizationExtractor.extract(request)
                .orElseThrow(TokenNotFoundException::new);
        if (jwtTokenProvider.validateToken(token)) {
            authenticationContext.setAuthority(jwtTokenProvider.getPayload(token));
            return true;
        }
        throw new InvalidTokenException();
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }
}

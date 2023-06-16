package com.woowacourse.auth.presentation.interceptor;

import com.woowacourse.auth.application.JwtTokenProvider;
import com.woowacourse.auth.exception.TokenNotFoundException;
import com.woowacourse.auth.presentation.AuthenticationContext;
import com.woowacourse.auth.support.AuthorizationExtractor;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String PROFILE_LOCAL = "local";

    @Autowired
    private final Environment environment;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    public LoginInterceptor(Environment environment, final JwtTokenProvider jwtTokenProvider,
                            final AuthenticationContext authenticationContext) {
        this.environment = environment;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        final String token = AuthorizationExtractor.extract(request)
                .orElseThrow(TokenNotFoundException::new);
        if (isLocalPhase()) {
            authenticationContext.setAuthenticationMember(token);
            return true;
        }
        authenticationContext.setAuthenticationMember(jwtTokenProvider.getPayload(token));
        return true;
    }

    private boolean isLocalPhase() {
        return Arrays.stream(environment.getActiveProfiles())
                .anyMatch(profile -> profile.equalsIgnoreCase(PROFILE_LOCAL));
    }
}

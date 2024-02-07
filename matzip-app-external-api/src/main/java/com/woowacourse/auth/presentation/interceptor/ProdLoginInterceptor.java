package com.woowacourse.auth.presentation.interceptor;

import static com.woowacourse.matzip.environment.ProfileUtil.PROD;
import static com.woowacourse.matzip.environment.ProfileUtil.TEST;

import com.woowacourse.auth.application.JwtTokenProvider;
import com.woowacourse.auth.exception.TokenNotFoundException;
import com.woowacourse.auth.presentation.AuthenticationContext;
import com.woowacourse.auth.support.AuthorizationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({PROD, TEST})
@Component
public class ProdLoginInterceptor implements LoginInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    public ProdLoginInterceptor(final JwtTokenProvider jwtTokenProvider,
                                final AuthenticationContext authenticationContext) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        final String token = AuthorizationExtractor.extract(request)
                .orElseThrow(TokenNotFoundException::new);
        authenticationContext.setAuthenticationMember(jwtTokenProvider.getPayload(token));
        return true;
    }
}

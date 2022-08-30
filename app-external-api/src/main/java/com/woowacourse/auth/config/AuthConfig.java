package com.woowacourse.auth.config;

import com.woowacourse.auth.presentation.AuthenticationArgumentResolver;
import com.woowacourse.auth.presentation.interceptor.LoginCheckerInterceptor;
import com.woowacourse.auth.presentation.interceptor.LoginInterceptor;
import com.woowacourse.auth.presentation.interceptor.PathMatcherInterceptor;
import com.woowacourse.auth.presentation.interceptor.PathMethod;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final LoginCheckerInterceptor loginCheckerInterceptor;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    public AuthConfig(final LoginInterceptor loginInterceptor,
                      final LoginCheckerInterceptor loginCheckerInterceptor,
                      final AuthenticationArgumentResolver authenticationArgumentResolver) {
        this.loginInterceptor = loginInterceptor;
        this.loginCheckerInterceptor = loginCheckerInterceptor;
        this.authenticationArgumentResolver = authenticationArgumentResolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginOrNotInterceptor());
        registry.addInterceptor(loginInterceptor());
    }

    private HandlerInterceptor loginInterceptor() {
        return new PathMatcherInterceptor(loginInterceptor)
                .excludePathPattern("/**", PathMethod.OPTIONS)
                .includePathPattern("/api/restaurants/*/reviews", PathMethod.POST);
    }

    private HandlerInterceptor loginOrNotInterceptor() {
        return new PathMatcherInterceptor(loginCheckerInterceptor)
                .includePathPattern("/api/restaurants/*/reviews", PathMethod.GET);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }
}

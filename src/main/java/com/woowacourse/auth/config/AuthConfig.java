package com.woowacourse.auth.config;

import com.woowacourse.auth.presentation.AuthenticationArgumentResolver;
import com.woowacourse.auth.presentation.interceptor.LoginInterceptor;
import com.woowacourse.auth.presentation.interceptor.NotLoginInterceptor;
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
    private final NotLoginInterceptor notLoginInterceptor;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    public AuthConfig(final LoginInterceptor loginInterceptor,
                      final AuthenticationArgumentResolver authenticationArgumentResolver) {
        this.loginInterceptor = loginInterceptor;
        this.notLoginInterceptor = new NotLoginInterceptor(loginInterceptor);
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
        return new PathMatcherInterceptor(notLoginInterceptor)
                .excludePathPattern("/api/restaurants/*/reviews", PathMethod.OPTIONS)
                .includePathPattern("/api/restaurants/*/reviews", PathMethod.GET);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }
}

package com.woowacourse.auth.config;

import com.woowacourse.auth.presentation.AuthenticationArgumentResolver;
import com.woowacourse.auth.presentation.LoginInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    public AuthConfig(final LoginInterceptor loginInterceptor,
                      final AuthenticationArgumentResolver authenticationArgumentResolver) {
        this.loginInterceptor = loginInterceptor;
        this.authenticationArgumentResolver = authenticationArgumentResolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/campuses/*/restaurants/*/reviews");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }
}

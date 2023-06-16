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

    private static final String REVIEWS_PATH = "/api/restaurants/*/reviews";
    private static final String RESTAURANT_DEMANDS_PATH = "/api/campuses/*/restaurantDemands";
    private static final String BOOKMARK_PATH = "/api/restaurants/*/bookmarks";

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
                .includePathPattern(REVIEWS_PATH, PathMethod.POST)
                .includePathPattern(REVIEWS_PATH + "/*", PathMethod.PUT)
                .includePathPattern(REVIEWS_PATH + "/*", PathMethod.DELETE)
                .includePathPattern(RESTAURANT_DEMANDS_PATH, PathMethod.POST)
                .includePathPattern(RESTAURANT_DEMANDS_PATH + "/*", PathMethod.PUT)
                .includePathPattern(RESTAURANT_DEMANDS_PATH + "/*", PathMethod.DELETE)
                .includePathPattern(BOOKMARK_PATH, PathMethod.POST)
                .includePathPattern(BOOKMARK_PATH, PathMethod.DELETE);
    }

    private HandlerInterceptor loginOrNotInterceptor() {
        return new PathMatcherInterceptor(loginCheckerInterceptor)
                .includePathPattern(REVIEWS_PATH, PathMethod.GET)
                .includePathPattern(RESTAURANT_DEMANDS_PATH, PathMethod.GET);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }
}

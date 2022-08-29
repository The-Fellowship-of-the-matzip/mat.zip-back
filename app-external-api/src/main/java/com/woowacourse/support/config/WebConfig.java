package com.woowacourse.support.config;

import com.woowacourse.support.logging.ApiQueryCountingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    private final ApiQueryCountingInterceptor apiQueryCountingInterceptor;

    public WebConfig(final ApiQueryCountingInterceptor apiQueryCountingInterceptor) {
        this.apiQueryCountingInterceptor = apiQueryCountingInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(apiQueryCountingInterceptor);
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders(HttpHeaders.LOCATION);
    }
}

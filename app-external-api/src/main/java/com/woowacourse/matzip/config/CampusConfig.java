package com.woowacourse.matzip.config;

import com.woowacourse.matzip.domain.campus.CampusCacheRepository;
import com.woowacourse.matzip.domain.campus.CampusRepository;
import com.woowacourse.matzip.presentation.CampusInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CampusConfig implements WebMvcConfigurer {

    private final CampusRepository campusRepository;

    public CampusConfig(final CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CampusInterceptor(campusCacheRepository()))
            .addPathPatterns("/api/campuses/**")
            .excludePathPatterns("/api/campuses");
    }

    @Bean
    public CampusCacheRepository campusCacheRepository() {
        return new CampusCacheRepository(campusRepository.findAll());
    }
}

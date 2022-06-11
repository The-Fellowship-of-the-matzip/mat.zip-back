package com.woowacourse.matzip.config;

import com.woowacourse.matzip.domain.campus.CampusCacheRepository;
import com.woowacourse.matzip.domain.campus.CampusRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CampusConfig implements WebMvcConfigurer {

    private final CampusRepository campusRepository;

    public CampusConfig(final CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    @Bean
    public CampusCacheRepository campusCacheRepository() {
        return new CampusCacheRepository(campusRepository.findAll());
    }
}

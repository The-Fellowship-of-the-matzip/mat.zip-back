package com.woowacourse.config;

import com.woowacourse.job.ReviewFailoverDateJobParameter;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    @JobScope
    public ReviewFailoverDateJobParameter jobParameter(@Value("#{jobParameters[date]}") String date) {
        return new ReviewFailoverDateJobParameter(date);
    }
}

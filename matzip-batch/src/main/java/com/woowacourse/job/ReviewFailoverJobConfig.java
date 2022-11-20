package com.woowacourse.job;

import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.EventStatus;
import com.woowacourse.matzip.domain.review.ReviewEvent;
import com.woowacourse.matzip.domain.review.ReviewEventRepository;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ReviewFailoverJobConfig {

    private static final int CHUNK_SIZE = 1000;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final ReviewFailoverDateJobParameter reviewFailoverDateJobParameter;
    private final RestaurantRepository restaurantRepository;
    private final ReviewEventRepository reviewEventRepository;

    @Bean
    public Job reviewFailoverJob() {
        return jobBuilderFactory.get("reviewFailoverJob")
                .listener(new ReviewFailoverJobListener())
                .start(
                        reviewFailoverStep()
                ).build();
    }

    @JobScope
    @Bean
    public Step reviewFailoverStep() {
        return stepBuilderFactory.get("reviewFailoverStep")
                .<ReviewEvent, ReviewEvent>chunk(CHUNK_SIZE)
                .reader(reviewEventReader())
                .writer(reviewEventWriter())
                .build();
    }

    @StepScope
    @Bean
    public JpaPagingItemReader<ReviewEvent> reviewEventReader() {
        JpaPagingItemReader<ReviewEvent> reader = new JpaPagingItemReader<>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("select re from ReviewEvent re "
                + "where re.executionDate = :date and re.eventStatus = :status");
        reader.setPageSize(CHUNK_SIZE);
        reader.setParameterValues(getParameters());
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("reviewEventReader");
        return reader;
    }

    private Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("date", reviewFailoverDateJobParameter.getDate());
        parameters.put("status", EventStatus.FAILED);
        return parameters;
    }

    @StepScope
    @Bean
    public ItemWriter<ReviewEvent> reviewEventWriter() {
        return items -> {
            for (ReviewEvent item : items) {
                restaurantRepository.updateRestaurantFailover(item.getRestaurantId(),
                        item.getRatingGap(),
                        item.getReviewCount());

                item.complete();
                reviewEventRepository.save(item);
            }
        };
    }
}

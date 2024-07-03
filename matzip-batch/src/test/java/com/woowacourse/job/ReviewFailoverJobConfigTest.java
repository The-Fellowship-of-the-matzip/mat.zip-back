package com.woowacourse.job;

import static com.woowacourse.matzip.TestFixtureCreateUtil.createTestRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.EventStatus;
import com.woowacourse.matzip.domain.review.Review;
import com.woowacourse.matzip.domain.review.ReviewEvent;
import com.woowacourse.matzip.domain.review.ReviewEventRepository;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBatchTest
@SpringBootTest
class ReviewFailoverJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewEventRepository reviewEventRepository;

    @Test
    void failover() throws Exception {
        Restaurant restaurant = restaurantRepository.save(createTestRestaurant(1L, 1L, "식당1", "주소1"));

        Member member = memberRepository.save(Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build());

        Review review1 = reviewRepository.save(Review.builder()
                .member(member)
                .restaurantId(restaurant.getId())
                .content("맛있어요")
                .rating(4)
                .menu("족발")
                .build());

        Review review2 = reviewRepository.save(Review.builder()
                .member(member)
                .restaurantId(restaurant.getId())
                .content("맛있어요")
                .rating(5)
                .menu("족발")
                .build());

        ReviewEvent reviewEvent1 = reviewEventRepository.save(new ReviewEvent(
                review1.getId(),
                review1.getRestaurantId(),
                review1.getRating(),
                1,
                LocalDate.now(),
                EventStatus.FAILED
        ));

        ReviewEvent reviewEvent2 = reviewEventRepository.save(new ReviewEvent(
                review2.getId(),
                review2.getRestaurantId(),
                review2.getRating(),
                1,
                LocalDate.now(),
                EventStatus.FAILED
        ));

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", LocalDate.now().toString())
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        ReviewEvent completedReviewEvent1 = reviewEventRepository.findById(reviewEvent1.getReviewId()).orElseThrow();
        ReviewEvent completedReviewEvent2 = reviewEventRepository.findById(reviewEvent2.getReviewId()).orElseThrow();
        Restaurant updatedRestaurant = restaurantRepository.findById(restaurant.getId()).orElseThrow();

        assertAll(
                () -> assertThat(completedReviewEvent1.getEventStatus()).isEqualTo(EventStatus.COMPLETE),
                () -> assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED),
                () -> assertThat(completedReviewEvent2.getEventStatus()).isEqualTo(EventStatus.COMPLETE),
                () -> assertThat(updatedRestaurant.getReviewCount()).isEqualTo(2),
                () -> assertThat(updatedRestaurant.getReviewRatingSum()).isEqualTo(9),
                () -> assertThat(updatedRestaurant.getReviewRatingAverage()).isEqualTo(4.5f)
        );
    }
}

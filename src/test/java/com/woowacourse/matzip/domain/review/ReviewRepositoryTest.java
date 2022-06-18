package com.woowacourse.matzip.domain.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 페이징값을_반환한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        for (int i = 1; i <= 20; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member))
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(4)
                    .menu("족발" + i)
                    .build());
        }
        Slice<Review> firstReviewPage = reviewRepository.findReviewsByRestaurantIdOrderByIdDesc(1L,
                PageRequest.of(0, 5));
        Slice<Review> secondReviewPage = reviewRepository.findReviewsByRestaurantIdOrderByIdDesc(1L,
                PageRequest.of(1, 5));

        assertAll(
                () -> assertThat(firstReviewPage).hasSize(5),
                () -> assertThat(firstReviewPage.getContent().get(0).getMenu()).isEqualTo("족발20"),
                () -> assertThat(secondReviewPage).hasSize(5),
                () -> assertThat(secondReviewPage.getContent().get(0).getMenu()).isEqualTo("족발15")
        );
    }

    @Test
    void 평균_별점을_반환한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        for (int i = 1; i <= 10; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member))
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(4)
                    .menu("족발" + i)
                    .build());
        }
        for (int i = 11; i <= 20; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member))
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(5)
                    .menu("족발" + i)
                    .build());
        }

        double average = reviewRepository.findAverageRatingUsingRestaurantId(1L)
                .orElse(0.0);

        assertThat(average).isEqualTo(4.5);
    }
}

package com.woowacourse.matzip.domain.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
@EnableJpaAuditing
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
        Slice<Review> firstReviewPage = reviewRepository.findPageByRestaurantIdOrderByIdDesc(1L,
                PageRequest.of(0, 5));
        Slice<Review> secondReviewPage = reviewRepository.findPageByRestaurantIdOrderByIdDesc(1L,
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

    @Test
    void 리뷰_저장_시_생성시간이_추가된다() {
        LocalDateTime currentTime = LocalDateTime.now();

        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        Review review = reviewRepository.save(Review.builder()
                .member(memberRepository.save(member))
                .restaurantId(1L)
                .content("맛있어요")
                .rating(5)
                .menu("족발")
                .build());

        assertThat(review.getCreatedAt()).isAfter(currentTime);
    }

    @Test
    void 유저들의_리뷰_개수를_조회한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        Member member2 = Member.builder()
                .githubId("githubId2")
                .username("username2")
                .profileImage("url2")
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
        for (int i = 1; i <= 5; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member2))
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(4)
                    .menu("족발" + i)
                    .build());
        }
        var reviewCountByMemberIds = reviewRepository.findReviewCountByMemberIds(List.of(member.getId(), member2.getId()));

        assertAll(
                () -> assertThat(reviewCountByMemberIds.get(0).getReviewCount()).isEqualTo(10),
                () -> assertThat(reviewCountByMemberIds.get(1).getReviewCount()).isEqualTo(5)
        );
    }
}

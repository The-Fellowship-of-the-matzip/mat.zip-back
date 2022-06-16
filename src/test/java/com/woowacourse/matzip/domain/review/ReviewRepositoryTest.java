package com.woowacourse.matzip.domain.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

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
                    .score(4)
                    .menu("족발" + i)
                    .build());
        }
        List<Review> firstReviewPage = reviewRepository.findReviewsByRestaurantIdOrderByIdDesc(1L, PageRequest.of(0, 5));
        List<Review> secondReviewPage = reviewRepository.findReviewsByRestaurantIdOrderByIdDesc(1L, PageRequest.of(1, 5));

        assertAll(
                () -> assertThat(firstReviewPage).hasSize(5),
                () -> assertThat(firstReviewPage.get(0).getMenu()).isEqualTo("족발20"),
                () -> assertThat(secondReviewPage).hasSize(5),
                () -> assertThat(secondReviewPage.get(0).getMenu()).isEqualTo("족발15")
        );
    }
}

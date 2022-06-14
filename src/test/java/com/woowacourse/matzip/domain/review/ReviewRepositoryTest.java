package com.woowacourse.matzip.domain.review;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.support.SpringServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@SpringServiceTest
class ReviewRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

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
                    .menu("족발")
                    .build());
        }
        List<Review> reviews = reviewRepository.findReviewsByRestaurantIdOrderByIdDesc(1L, PageRequest.of(2, 5));
        assertThat(reviews).hasSize(5);
    }
}

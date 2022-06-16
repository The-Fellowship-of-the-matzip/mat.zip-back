package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.HUNI;
import static com.woowacourse.matzip.support.ReviewFixtures.REVIEW_1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import com.woowacourse.support.SpringServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringServiceTest
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static ReviewCreateRequest reviewCreateRequest() {
        return new ReviewCreateRequest(REVIEW_1.getContent(), REVIEW_1.getScore(), REVIEW_1.getMenu());
    }

    @Test
    void 없는_유저가_리뷰작성하는_경우_예외발생() {
        assertThatThrownBy(() -> reviewService.createReview("nonUser", 1L, reviewCreateRequest()))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void 리뷰를_작성한다() {
        Member member = memberRepository.save(HUNI.toMember());
        Restaurant restaurant = restaurantRepository.findAll().get(0);

        assertDoesNotThrow(() -> reviewService.createReview(member.getGithubId(), restaurant.getId(), reviewCreateRequest()));
    }
}

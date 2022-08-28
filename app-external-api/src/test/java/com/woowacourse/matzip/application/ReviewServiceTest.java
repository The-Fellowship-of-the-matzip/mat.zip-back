package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.ORI;
import static com.woowacourse.matzip.support.ReviewFixtures.REVIEW_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.application.response.ReviewsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import com.woowacourse.support.SpringServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

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
        Member member = memberRepository.save(ORI.toMember());
        Restaurant restaurant = restaurantRepository.findAll().get(0);

        assertDoesNotThrow(
                () -> reviewService.createReview(member.getGithubId(), restaurant.getId(), reviewCreateRequest()));
    }

    @Test
    void 리뷰를_조회한다() {
        Member member = memberRepository.save(ORI.toMember());
        Restaurant restaurant = restaurantRepository.findAll().get(0);
        for (int i = 0; i < 3; i++) {
            reviewService.createReview(member.getGithubId(), restaurant.getId(), reviewCreateRequest());
        }

        ReviewsResponse page1 = reviewService.findPageByRestaurantId(restaurant.getId(), PageRequest.of(0, 2));
        ReviewsResponse page2 = reviewService.findPageByRestaurantId(restaurant.getId(), PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(page1.getReviews()).hasSize(2),
                () -> assertThat(page1.isHasNext()).isTrue(),
                () -> assertThat(page2.getReviews()).hasSize(1),
                () -> assertThat(page2.isHasNext()).isFalse()
        );
    }
}

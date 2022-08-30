package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.HUNI;
import static com.woowacourse.auth.support.GithubResponseFixtures.ORI;
import static com.woowacourse.matzip.support.ReviewFixtures.REVIEW_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.application.response.ReviewResponse;
import com.woowacourse.matzip.application.response.ReviewsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import com.woowacourse.matzip.presentation.request.ReviewUpdateRequest;
import com.woowacourse.support.SpringServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@SpringServiceTest
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

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

        ReviewsResponse page1 = reviewService.findPageByRestaurantId(null, restaurant.getId(), PageRequest.of(0, 2));
        ReviewsResponse page2 = reviewService.findPageByRestaurantId(null, restaurant.getId(), PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(page1.getReviews()).hasSize(2),
                () -> assertThat(page1.isHasNext()).isTrue(),
                () -> assertThat(page2.getReviews()).hasSize(1),
                () -> assertThat(page2.isHasNext()).isFalse()
        );
    }

    @Test
    void 리뷰_조회시_변경가능한지_확인한다() {
        Member ori = memberRepository.save(ORI.toMember());
        Member huni = memberRepository.save(HUNI.toMember());
        Restaurant restaurant = restaurantRepository.findAll().get(0);
        for (int i = 0; i < 3; i++) {
            reviewService.createReview(ori.getGithubId(), restaurant.getId(), reviewCreateRequest());
        }
        reviewService.createReview(huni.getGithubId(), restaurant.getId(), reviewCreateRequest());

        ReviewsResponse page1 = reviewService.findPageByRestaurantId(ori.getGithubId(), restaurant.getId(), PageRequest.of(0, 2));
        ReviewsResponse page2 = reviewService.findPageByRestaurantId(ori.getGithubId(), restaurant.getId(), PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(page1.getReviews()).extracting("updatable").containsExactly(false, true),
                () -> assertThat(page2.getReviews()).extracting("updatable").containsExactly(true, true)
        );
    }

    @Test
    void 리뷰를_수정한다() {
        Member member = memberRepository.save(ORI.toMember());
        Restaurant restaurant = restaurantRepository.findAll().get(0);
        reviewService.createReview(member.getGithubId(), restaurant.getId(), reviewCreateRequest());

        Long reviewId = reviewService.findPageByRestaurantId(member.getGithubId(), restaurant.getId(), PageRequest.of(0, 1))
                .getReviews()
                .get(0)
                .getId();

        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest("내용", 5, "메뉴");
        reviewService.updateReview(member.getGithubId(), reviewId, reviewUpdateRequest);

        ReviewResponse reviewResponse = reviewService.findPageByRestaurantId(member.getGithubId(), restaurant.getId(),
                        PageRequest.of(0, 1))
                .getReviews()
                .get(0);
        assertAll(
                () -> assertThat(reviewResponse.getContent()).isEqualTo("내용"),
                () -> assertThat(reviewResponse.getRating()).isEqualTo(5),
                () -> assertThat(reviewResponse.getMenu()).isEqualTo("메뉴")
        );
    }

    @Test
    void 리뷰를_삭제한다() {
        Member member = memberRepository.save(ORI.toMember());
        Restaurant restaurant = restaurantRepository.findAll().get(0);
        reviewService.createReview(member.getGithubId(), restaurant.getId(), reviewCreateRequest());

        Long reviewId = reviewService.findPageByRestaurantId(member.getGithubId(), restaurant.getId(), PageRequest.of(0, 1))
                .getReviews()
                .get(0)
                .getId();

        reviewService.deleteReview(member.getGithubId(), reviewId);
        assertThat(reviewRepository.findById(reviewId).isEmpty()).isTrue();
    }
}

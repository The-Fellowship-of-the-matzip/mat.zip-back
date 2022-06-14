package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.HUNI;
import static com.woowacourse.matzip.support.CategoryFixtures.한식;
import static com.woowacourse.matzip.support.RestaurantFixture.RESTAURANT_1;
import static com.woowacourse.matzip.support.ReviewFixtures.REVIEW_1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.campus.CampusRepository;
import com.woowacourse.matzip.domain.category.Category;
import com.woowacourse.matzip.domain.category.CategoryRepository;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.ReviewRepository;
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
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CampusRepository campusRepository;

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
        Campus campus = campusRepository.findAll().get(0);
        Category category = categoryRepository.save(한식.toCategory());
        Restaurant restaurant = restaurantRepository
                .save(RESTAURANT_1.toRestaurantWithCategoryIdAndCampusId(category.getId(), campus.getId()));

        assertDoesNotThrow(
                () -> reviewService.createReview(member.getGithubId(), restaurant.getId(), reviewCreateRequest()));
    }
}

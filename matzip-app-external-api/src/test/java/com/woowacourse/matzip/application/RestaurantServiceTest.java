package com.woowacourse.matzip.application;

import static com.woowacourse.matzip.TestFixtureCreateUtil.createTestMember;
import static com.woowacourse.matzip.TestFixtureCreateUtil.createTestRestaurant;
import static com.woowacourse.matzip.TestFixtureCreateUtil.createTestReview;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.application.response.RestaurantResponse;
import com.woowacourse.matzip.application.response.RestaurantTitleResponse;
import com.woowacourse.matzip.application.response.RestaurantTitlesResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.matzip.exception.RestaurantNotFoundException;
import com.woowacourse.support.SpringServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@SpringServiceTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 캠퍼스id가_일치하는_식당_목록_페이지를_최신순으로_반환한다() {
        RestaurantTitlesResponse page1 = restaurantService.findByCampusIdAndCategoryId("DEFAULT", 2L, null,
                PageRequest.of(0, 2));
        RestaurantTitlesResponse page2 = restaurantService.findByCampusIdAndCategoryId("DEFAULT", 2L, null,
                PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(page1.getRestaurants()).hasSize(2)
                        .extracting(RestaurantTitleResponse::getName)
                        .containsExactly("마담밍3", "마담밍2"),
                () -> assertThat(page1.isHasNext()).isTrue(),
                () -> assertThat(page2.getRestaurants()).hasSize(2)
                        .extracting(RestaurantTitleResponse::getName)
                        .containsExactly("마담밍", "뽕나무쟁이 선릉본점"),
                () -> assertThat(page2.isHasNext()).isTrue()
        );
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당_목록_페이지를_반환한다() {
        RestaurantTitlesResponse response = restaurantService.findByCampusIdAndCategoryId("DEFAULT", 2L, 1L,
                PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(response.getRestaurants()).hasSize(2)
                        .extracting(RestaurantTitleResponse::getName)
                        .containsExactly("뽕나무쟁이 선릉본점", "배가무닭볶음탕"),
                () -> assertThat(response.isHasNext()).isFalse()
        );
    }

    @Test
    void 식당_목록_조회시_평균별점도_조회한다() {
        Member member = createTestMember();
        memberRepository.save(member);
        Restaurant restaurant = restaurantRepository.save(createTestRestaurant(1L, 1L, "테스트식당", "테스트주소"));
        for (int i = 1; i <= 10; i++) {
            reviewRepository.save(createTestReview(member, restaurant.getId(), 4));
        }
        restaurantRepository.save(createTestRestaurant(restaurant, 10, 40, 4.0f));
        restaurantRepository.save(restaurant);

        RestaurantTitlesResponse response = restaurantService.findByCampusIdAndCategoryId("DEFAULT", 1L, 1L,
                PageRequest.of(0, 10));

        assertThat(response.getRestaurants()).hasSize(1)
                .extracting(RestaurantTitleResponse::getRating)
                .containsExactly(4.0);
    }

    @Test
    void 캠퍼스id가_일치하는_식당_목록을_가나다순으로_정렬한_페이지를_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "가식당", "테스트주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "다식당", "테스트주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "나식당", "테스트주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        RestaurantTitlesResponse response = restaurantService.findByCampusIdAndCategoryId("SPELL", 1L, 1L,
                PageRequest.of(0, 3));

        assertThat(response.getRestaurants()).hasSize(3)
                .extracting("id")
                .containsExactly(restaurant1.getId(), restaurant3.getId(), restaurant2.getId());
    }

    @Test
    void 캠퍼스id가_일치하는_식당_목록을_평균_별점순으로_정렬한_페이지를_반환한다() {
        Member member = createTestMember();
        memberRepository.save(member);
        Restaurant restaurant1 = restaurantRepository.save(createTestRestaurant(1L, 1L, "테스트식당1", "테스트주소1"));
        Restaurant restaurant2 = restaurantRepository.save(createTestRestaurant(1L, 1L, "테스트식당2", "테스트주소2"));
        for (int i = 0; i < 10; i++) {
            reviewRepository.save(createTestReview(member, restaurant1.getId(), 4));
            reviewRepository.save(createTestReview(member, restaurant2.getId(), 3));
        }
        restaurantRepository.save(createTestRestaurant(restaurant1, 10, 40, 4.0f));
        restaurantRepository.save(createTestRestaurant(restaurant2, 10, 30, 3.0f));

        RestaurantTitlesResponse response = restaurantService.findByCampusIdAndCategoryId("RATING", 1L, 1L,
                PageRequest.of(0, 2));

        assertThat(response.getRestaurants()).hasSize(2)
                .extracting("id")
                .containsExactly(restaurant1.getId(), restaurant2.getId());
    }

    @Test
    void 무작위로_지정한_캠퍼스의_지정한_개수의_식당_목록을_조회한다() {
        List<RestaurantTitleResponse> responses = restaurantService.findRandomsByCampusId(2L, 2);

        assertThat(responses).hasSize(2)
                .extracting("name")
                .containsAnyOf("마담밍", "마담밍2", "마담밍3", "뽕나무쟁이 선릉본점", "배가무닭볶음탕");
    }

    @Test
    void id로_식당의_상세_정보를_조회한다() {
        Restaurant restaurant = createTestRestaurant(1L, 1L, "테스트식당", "테스트주소");
        restaurantRepository.save(restaurant);

        RestaurantResponse response = restaurantService.findById(restaurant.getId());

        assertAll(
                () -> assertThat(response).usingRecursiveComparison()
                        .ignoringFields("rating")
                        .isEqualTo(restaurant),
                () -> assertThat(response.getRating()).isEqualTo(0.0)
        );
    }

    @Test
    void 존재하지_않는_id로_조회하면_예외를_반환한다() {
        assertThatThrownBy(() -> restaurantService.findById(Long.MAX_VALUE))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void 이름이_일치하는_식당목록을_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "테스트식당1", "테스트주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "테스트식당2", "테스트주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "테스트식당3", "테스트주소3");
        Restaurant restaurant4 = createTestRestaurant(1L, 1L, "테스트식당4", "테스트주소4");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3, restaurant4));

        RestaurantTitlesResponse response1 = restaurantService.findTitlesByCampusIdAndNameContainingIgnoreCaseIdDescSort(
                1L, "테스트",
                PageRequest.of(0, 2));
        RestaurantTitlesResponse response2 = restaurantService.findTitlesByCampusIdAndNameContainingIgnoreCaseIdDescSort(
                1L, "테스트",
                PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(response1.getRestaurants()).hasSize(2)
                        .extracting("id")
                        .containsExactly(restaurant4.getId(), restaurant3.getId()),
                () -> assertThat(response1.isHasNext()).isTrue(),
                () -> assertThat(response2.getRestaurants()).hasSize(2)
                        .extracting("id")
                        .containsExactly(restaurant2.getId(), restaurant1.getId()),
                () -> assertThat(response2.isHasNext()).isFalse()
        );
    }

    @Test
    void 리뷰_작성에_대해_음식점_정보를_수정한다() {
        Restaurant restaurant = restaurantRepository.save(
                Restaurant.builder()
                        .campusId(1L)
                        .categoryId(1L)
                        .name("음식점")
                        .address("주소")
                        .kakaoMapUrl("URL")
                        .imageUrl("이미지 주소")
                        .distance(0)
                        .build()
        );

        restaurantService.updateWhenReviewCreate(restaurant.getId(), 5);

        Restaurant actual = restaurantRepository.findById(restaurant.getId())
                .orElseThrow();
        assertAll(
                () -> assertThat(actual.getReviewCount()).isOne(),
                () -> assertThat(actual.getReviewRatingSum()).isEqualTo(5),
                () -> assertThat(actual.getReviewRatingAverage()).isEqualTo(5.0f)
        );
    }

    @Test
    void 리뷰_삭제_이벤트를_바탕으로_음식점_정보를_수정한다() {
        Restaurant restaurant = restaurantRepository.save(
                Restaurant.builder()
                        .campusId(1L)
                        .categoryId(1L)
                        .name("음식점")
                        .address("주소")
                        .kakaoMapUrl("URL")
                        .imageUrl("이미지 주소")
                        .distance(0)
                        .reviewCount(1)
                        .reviewRatingSum(5)
                        .reviewRatingAverage(5.0f)
                        .build()
        );

        restaurantService.updateWhenReviewDelete(restaurant.getId(), 5);

        Restaurant actual = restaurantRepository.findById(restaurant.getId())
                .orElseThrow();
        assertAll(
                () -> assertThat(actual.getReviewCount()).isZero(),
                () -> assertThat(actual.getReviewRatingSum()).isZero(),
                () -> assertThat(actual.getReviewRatingAverage()).isZero()
        );
    }

    @Test
    void 리뷰_수정_이벤트를_바탕으로_음식점_정보를_수정한다() {
        Restaurant restaurant = restaurantRepository.save(
                Restaurant.builder()
                        .campusId(1L)
                        .categoryId(1L)
                        .name("음식점")
                        .address("주소")
                        .kakaoMapUrl("URL")
                        .imageUrl("이미지 주소")
                        .distance(0)
                        .reviewCount(1)
                        .reviewRatingSum(5)
                        .reviewRatingAverage(5.0f)
                        .build()
        );

        restaurantService.updateWhenReviewUpdate(restaurant.getId(), -3);

        Restaurant actual = restaurantRepository.findById(restaurant.getId())
                .orElseThrow();
        assertAll(
                () -> assertThat(actual.getReviewCount()).isOne(),
                () -> assertThat(actual.getReviewRatingSum()).isEqualTo(2),
                () -> assertThat(actual.getReviewRatingAverage()).isEqualTo(2.0f)
        );
    }
}

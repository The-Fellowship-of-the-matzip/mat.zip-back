package com.woowacourse.matzip.application;

import static com.woowacourse.matzip.domain.restaurant.SortCondition.DEFAULT;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestMember;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestRestaurant;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestReview;
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
    void 캠퍼스id가_일치하는_식당_목록_페이지를_반환한다() {
        RestaurantTitlesResponse page1 = restaurantService.findByCampusId(2L, null,
                PageRequest.of(0, 2, DEFAULT.getValue()));
        RestaurantTitlesResponse page2 = restaurantService.findByCampusId(2L, null,
                PageRequest.of(1, 2, DEFAULT.getValue()));

        assertAll(
                () -> assertThat(page1.getRestaurants()).hasSize(2)
                        .extracting(RestaurantTitleResponse::getName)
                        .containsExactly("마담밍", "뽕나무쟁이 선릉본점"),
                () -> assertThat(page1.isHasNext()).isTrue(),
                () -> assertThat(page2.getRestaurants()).hasSize(1)
                        .extracting(RestaurantTitleResponse::getName)
                        .containsExactly("배가무닭볶음탕"),
                () -> assertThat(page2.isHasNext()).isFalse()
        );
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당_목록_페이지를_반환한다() {
        RestaurantTitlesResponse response = restaurantService.findByCampusId(2L, 1L,
                PageRequest.of(0, 2, DEFAULT.getValue()));

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
        Restaurant restaurant = createTestRestaurant(1L, 1L, "테스트식당", "테스트주소");
        restaurantRepository.save(restaurant);
        for (int i = 1; i <= 10; i++) {
            reviewRepository.save(createTestReview(member, restaurant.getId(), 4));
        }

        RestaurantTitlesResponse response = restaurantService.findByCampusId(1L, 1L,
                PageRequest.of(0, 10, DEFAULT.getValue()));

        assertThat(response.getRestaurants()).hasSize(1)
                .extracting(RestaurantTitleResponse::getRating)
                .containsExactly(4.0);
    }

    @Test
    void 캠퍼스id가_일치하는_식당_목록을_평균_별점_순으로_정렬한_페이지를_반환한다() {
        Member member = createTestMember();
        memberRepository.save(member);
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "테스트식당1", "테스트주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "테스트식당2", "테스트주소2");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2));
        for (int i = 0; i < 10; i++) {
            reviewRepository.save(createTestReview(member, restaurant1.getId(), 4));
            reviewRepository.save(createTestReview(member, restaurant2.getId(), 3));
        }

        RestaurantTitlesResponse response = restaurantService.findByCampusIdOrderByRatingDesc(1L, 1L,
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
                .containsAnyOf("마담밍", "뽕나무쟁이 선릉본점", "배가무닭볶음탕");
    }

    @Test
    void id로_식당의_상세_정보를_조회한다() {
        Restaurant restaurant = createTestRestaurant(1L, 1L, "테스트식당", "테스트주소");
        restaurantRepository.save(restaurant);

        RestaurantResponse response = restaurantService.findById(restaurant.getId());

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(restaurant);
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

        RestaurantTitlesResponse response1 = restaurantService.findTitlesByCampusIdAndNameContainingIgnoreCaseIdDescSort(1L, "테스트",
                PageRequest.of(0, 2));
        RestaurantTitlesResponse response2 = restaurantService.findTitlesByCampusIdAndNameContainingIgnoreCaseIdDescSort(1L, "테스트",
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
}

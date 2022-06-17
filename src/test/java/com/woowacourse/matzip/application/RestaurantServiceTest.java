package com.woowacourse.matzip.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.application.response.RestaurantResponse;
import com.woowacourse.matzip.application.response.RestaurantTitleResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.Review;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.matzip.exception.RestaurantNotFoundException;
import com.woowacourse.support.SpringServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        List<RestaurantTitleResponse> page1 = restaurantService.findByCampusIdOrderByIdDesc(2L, null,
                Pageable.ofSize(2));
        List<RestaurantTitleResponse> page2 = restaurantService.findByCampusIdOrderByIdDesc(2L, null,
                PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(page1).hasSize(2)
                        .extracting(RestaurantTitleResponse::getName)
                        .containsExactly("마담밍", "뽕나무쟁이 선릉본점"),
                () -> assertThat(page2).hasSize(1)
                        .extracting(RestaurantTitleResponse::getName)
                        .containsExactly("배가무닭볶음탕")
        );
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당_목록_페이지를_반환한다() {
        List<RestaurantTitleResponse> responses = restaurantService.findByCampusIdOrderByIdDesc(2L, 1L,
                Pageable.ofSize(2));

        assertThat(responses).hasSize(2)
                .extracting(RestaurantTitleResponse::getName)
                .containsExactly("뽕나무쟁이 선릉본점", "배가무닭볶음탕");
    }

    @Test
    void 식당_목록_조회시_평균별점도_조회한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        Restaurant restaurant = Restaurant.builder()
                .campusId(1L)
                .categoryId(1L)
                .name("테스트식당")
                .address("테스트주소")
                .kakaoMapUrl("testURL")
                .imageUrl("testURL")
                .build();
        restaurantRepository.save(restaurant);
        for (int i = 1; i <= 10; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member))
                    .restaurantId(restaurant.getId())
                    .content("맛있어요")
                    .rating(4)
                    .menu("족발" + i)
                    .build());
        }

        List<RestaurantTitleResponse> responses = restaurantService.findByCampusIdOrderByIdDesc(1L, 1L,
                Pageable.ofSize(10));

        assertThat(responses).hasSize(1)
                .extracting(RestaurantTitleResponse::getRating)
                .containsExactly(4.0);
    }

    @Test
    void id로_식당의_상세_정보를_조회한다() {
        Restaurant restaurant = Restaurant.builder()
                .campusId(1L)
                .categoryId(1L)
                .name("테스트식당")
                .address("테스트주소")
                .kakaoMapUrl("testURL")
                .imageUrl("testURL")
                .build();
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
}

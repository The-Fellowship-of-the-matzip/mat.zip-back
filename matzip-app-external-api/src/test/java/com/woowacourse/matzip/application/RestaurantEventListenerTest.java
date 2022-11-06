package com.woowacourse.matzip.application;

import static com.woowacourse.matzip.RestaurantFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.woowacourse.matzip.RestaurantFixture;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.Review;
import com.woowacourse.matzip.domain.review.ReviewCreateEvent;
import com.woowacourse.matzip.domain.review.ReviewDeleteEvent;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.matzip.domain.review.ReviewUpdateEvent;
import com.woowacourse.support.SpringServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringServiceTest
class RestaurantEventListenerTest {

    @Autowired
    private RestaurantEventListener restaurantEventListener;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 리뷰_작성_이벤트를_바탕으로_음식점_정보를_수정한다() {
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
        ReviewCreateEvent event = new ReviewCreateEvent(restaurant.getId(), 5);

        restaurantEventListener.updateRestaurantByReviewCreate(event);

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
        ReviewDeleteEvent event = new ReviewDeleteEvent(restaurant.getId(), 5);

        restaurantEventListener.updateRestaurantByReviewDelete(event);

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
        ReviewUpdateEvent event = new ReviewUpdateEvent(restaurant.getId(), -3);

        restaurantEventListener.updateRestaurantByReviewUpdate(event);

        Restaurant actual = restaurantRepository.findById(restaurant.getId())
                .orElseThrow();
        assertAll(
                () -> assertThat(actual.getReviewCount()).isOne(),
                () -> assertThat(actual.getReviewRatingSum()).isEqualTo(2),
                () -> assertThat(actual.getReviewRatingAverage()).isEqualTo(2.0f)
        );
    }
}

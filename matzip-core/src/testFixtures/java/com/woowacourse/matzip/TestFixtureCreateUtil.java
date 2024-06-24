package com.woowacourse.matzip;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import com.woowacourse.matzip.domain.review.Review;

public class TestFixtureCreateUtil {

    public static Restaurant createTestRestaurant(Long categoryId, Long campusId, String name, String address) {
        return Restaurant.builder()
                .categoryId(categoryId)
                .campusId(campusId)
                .name(name)
                .address(address)
                .distance(10)
                .kakaoMapUrl("테스트URL")
                .imageUrl("테스트URL")
                .build();
    }

    public static Restaurant createTestRestaurant(Long categoryId, Long campusId, String name, String address, long distance) {
        return Restaurant.builder()
                .categoryId(categoryId)
                .campusId(campusId)
                .name(name)
                .address(address)
                .distance(distance)
                .kakaoMapUrl("테스트URL")
                .imageUrl("테스트URL")
                .build();
    }

    public static Restaurant createTestRestaurant(final Restaurant restaurant, final int reviewCount, final long reviewRatingSum, final float reviewRatingAverage) {
        return Restaurant.builder()
                .id(restaurant.getId())
                .categoryId(restaurant.getCategoryId())
                .campusId(restaurant.getCampusId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .distance(restaurant.getDistance())
                .kakaoMapUrl(restaurant.getKakaoMapUrl())
                .imageUrl(restaurant.getImageUrl())
                .reviewCount(reviewCount)
                .reviewRatingSum(reviewRatingSum)
                .reviewRatingAverage(reviewRatingAverage)
                .build();
    }

    public static Member createTestMember() {
        return Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
    }

    public static Review createTestReview(Member member, Long restaurantId, int rating) {
        return Review.builder()
                .member(member)
                .restaurantId(restaurantId)
                .content("맛있어요")
                .rating(rating)
                .menu("메뉴")
                .build();
    }

    public static RestaurantDemand createTestRestaurantDemand(Long categoryId, Long campusId, String name,
                                                              Member member) {
        return RestaurantDemand.builder()
                .categoryId(categoryId)
                .campusId(campusId)
                .name(name)
                .member(member)
                .build();
    }
}

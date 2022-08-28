package com.woowacourse.matzip.support;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
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
}

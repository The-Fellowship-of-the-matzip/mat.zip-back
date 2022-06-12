package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantResponse {

    private Long id;
    private Long categoryId;
    private Long campusId;
    private String name;
    private String address;
    private Long distance;
    private String kakaoMapUrl;
    private String imageUrl;

    private RestaurantResponse() {
    }

    private RestaurantResponse(final Long id, final Long categoryId, final Long campusId, final String name,
                               final String address, final Long distance, final String kakaoMapUrl,
                               final String imageUrl) {
        this.id = id;
        this.categoryId = categoryId;
        this.campusId = campusId;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.kakaoMapUrl = kakaoMapUrl;
        this.imageUrl = imageUrl;
    }

    public static RestaurantResponse from(final Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(), restaurant.getCategoryId(), restaurant.getCampusId(),
                restaurant.getName(), restaurant.getAddress(), restaurant.getDistance(), restaurant.getKakaoMapUrl(),
                restaurant.getImageUrl());
    }
}

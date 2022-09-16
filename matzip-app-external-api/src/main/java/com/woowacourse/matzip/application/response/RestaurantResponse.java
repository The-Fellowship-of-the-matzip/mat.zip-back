package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantResponse {

    private Long id;
    private String name;
    private String address;
    private long distance;
    private double rating;
    private String kakaoMapUrl;
    private String imageUrl;

    private RestaurantResponse() {
    }

    private RestaurantResponse(final Long id, final String name, final String address, final long distance,
                               final double rating, final String kakaoMapUrl, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.rating = rating;
        this.kakaoMapUrl = kakaoMapUrl;
        this.imageUrl = imageUrl;
    }

    public static RestaurantResponse of(final Restaurant restaurant, final double rating) {
        return new RestaurantResponse(restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getDistance(),
                rating,
                restaurant.getKakaoMapUrl(),
                restaurant.getImageUrl());
    }
}

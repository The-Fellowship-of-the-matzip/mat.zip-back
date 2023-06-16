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
    private boolean saved;

    private RestaurantResponse() {
    }

    private RestaurantResponse(final Long id, final String name, final String address, final long distance,
                               final double rating, final String kakaoMapUrl, final String imageUrl,
                               final boolean saved) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.rating = rating;
        this.kakaoMapUrl = kakaoMapUrl;
        this.imageUrl = imageUrl;
        this.saved = saved;
    }

    public static RestaurantResponse of(final Restaurant restaurant, final double rating, final boolean saved) {
        return new RestaurantResponse(restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getDistance(),
                rating,
                restaurant.getKakaoMapUrl(),
                restaurant.getImageUrl(),
                saved);
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}

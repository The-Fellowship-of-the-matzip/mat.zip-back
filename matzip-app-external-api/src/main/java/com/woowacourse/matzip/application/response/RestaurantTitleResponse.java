package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantTitleResponse {

    private Long id;
    private String name;
    private String address;
    private long distance;
    private double rating;
    private String imageUrl;
    private int reviewCount;
    private boolean saved;

    private RestaurantTitleResponse() {
    }

    private RestaurantTitleResponse(Long id, String name, String address, long distance, double rating, String imageUrl,
                                    int reviewCount, boolean saved) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.reviewCount = reviewCount;
        this.saved = saved;
    }

    public static RestaurantTitleResponse of(final Restaurant restaurant, final double rating, final boolean saved) {
        return new RestaurantTitleResponse(restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getDistance(),
                rating,
                restaurant.getImageUrl(),
                restaurant.getReviewCount(),
                saved
        );
    }
}

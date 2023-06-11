package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantTitleResponse {

    private Long id;
    private String name;
    private long distance;
    private double rating;
    private String imageUrl;
    private int reviewCount;

    private RestaurantTitleResponse() {
    }

    private RestaurantTitleResponse(Long id, String name, long distance, double rating, String imageUrl, int reviewCount) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.reviewCount = reviewCount;
    }

    public static RestaurantTitleResponse of(final Restaurant restaurant, final double rating) {
        return new RestaurantTitleResponse(restaurant.getId(),
                restaurant.getName(),
                restaurant.getDistance(),
                rating,
                restaurant.getImageUrl(),
                restaurant.getReviewCount()
        );
    }
}

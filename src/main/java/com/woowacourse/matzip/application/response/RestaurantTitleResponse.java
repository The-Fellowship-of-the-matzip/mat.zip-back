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

    private RestaurantTitleResponse() {
    }

    private RestaurantTitleResponse(Long id, String name, long distance, double rating, String imageUrl) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public static RestaurantTitleResponse of(Restaurant restaurant, double rating) {
        return new RestaurantTitleResponse(restaurant.getId(), restaurant.getName(), restaurant.getDistance(), rating,
                restaurant.getImageUrl());
    }
}

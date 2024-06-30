package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantSearchResponse {
    private Long id;
    private String name;

    private RestaurantSearchResponse() {
    }

    public RestaurantSearchResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static RestaurantSearchResponse from(Restaurant restaurant) {
        return new RestaurantSearchResponse(restaurant.getId(), restaurant.getName());
    }
}

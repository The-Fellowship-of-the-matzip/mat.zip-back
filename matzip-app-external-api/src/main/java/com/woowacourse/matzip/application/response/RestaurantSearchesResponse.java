package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import lombok.Getter;

import java.util.List;

@Getter
public class RestaurantSearchesResponse {
    List<RestaurantSearchResponse> restaurants;

    public RestaurantSearchesResponse() {
    }

    public RestaurantSearchesResponse(List<RestaurantSearchResponse> restaurants) {
        this.restaurants = restaurants;
    }

    public static RestaurantSearchesResponse from(List<Restaurant> restaurants) {
        List<RestaurantSearchResponse> items = restaurants.stream()
                .map(RestaurantSearchResponse::from)
                .toList();

        return new RestaurantSearchesResponse(items);
    }
}

package com.woowacourse.matzip.application.response;

import java.util.List;
import lombok.Getter;

@Getter
public class RestaurantTitlesResponse {

    private boolean hasNext;
    private List<RestaurantTitleResponse> restaurants;

    private RestaurantTitlesResponse() {
    }

    public RestaurantTitlesResponse(boolean hasNext, List<RestaurantTitleResponse> restaurants) {
        this.hasNext = hasNext;
        this.restaurants = restaurants;
    }
}

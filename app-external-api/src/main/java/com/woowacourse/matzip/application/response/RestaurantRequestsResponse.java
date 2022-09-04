package com.woowacourse.matzip.application.response;

import java.util.List;
import lombok.Getter;

@Getter
public class RestaurantRequestsResponse {

    private List<RestaurantRequestResponse> items;
    private boolean hasNext;

    private RestaurantRequestsResponse() {
    }

    public RestaurantRequestsResponse(final List<RestaurantRequestResponse> items, final boolean hasNext) {
        this.items = items;
        this.hasNext = hasNext;
    }
}

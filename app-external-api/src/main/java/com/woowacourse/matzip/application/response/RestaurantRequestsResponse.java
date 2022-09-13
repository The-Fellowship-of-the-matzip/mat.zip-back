package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.data.domain.Slice;

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

    public static RestaurantRequestsResponse of(final Slice<RestaurantRequest> page, final String githubId) {
        List<RestaurantRequestResponse> items = page.getContent()
                .stream()
                .map(restaurantRequest -> RestaurantRequestResponse.of(restaurantRequest, githubId))
                .collect(Collectors.toList());
        return new RestaurantRequestsResponse(items, page.hasNext());
    }
}

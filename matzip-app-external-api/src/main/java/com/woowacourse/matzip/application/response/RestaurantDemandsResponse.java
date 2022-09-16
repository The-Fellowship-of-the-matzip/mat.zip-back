package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class RestaurantDemandsResponse {

    private List<RestaurantDemandResponse> items;
    private boolean hasNext;

    private RestaurantDemandsResponse() {
    }

    public RestaurantDemandsResponse(final List<RestaurantDemandResponse> items, final boolean hasNext) {
        this.items = items;
        this.hasNext = hasNext;
    }

    public static RestaurantDemandsResponse of(final Slice<RestaurantDemand> page, final String githubId) {
        List<RestaurantDemandResponse> items = page.getContent()
                .stream()
                .map(restaurantRequest -> RestaurantDemandResponse.of(restaurantRequest, githubId))
                .collect(Collectors.toList());
        return new RestaurantDemandsResponse(items, page.hasNext());
    }
}

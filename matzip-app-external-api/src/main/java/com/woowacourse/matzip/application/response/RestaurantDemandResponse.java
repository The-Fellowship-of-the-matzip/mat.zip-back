package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import lombok.Getter;

@Getter
public class RestaurantDemandResponse {

    private Long id;
    private Long categoryId;
    private String name;
    private String author;
    private boolean updatable;
    private boolean registered;

    private RestaurantDemandResponse() {
    }

    public RestaurantDemandResponse(final Long id, final Long categoryId, final String name, final String author,
                                    final boolean updatable, final boolean registered) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.author = author;
        this.updatable = updatable;
        this.registered = registered;
    }

    public static RestaurantDemandResponse of(final RestaurantDemand restaurantDemand, final String githubId) {
        String authorGithubId = restaurantDemand.getMember()
                .getGithubId();
        return new RestaurantDemandResponse(
                restaurantDemand.getId(),
                restaurantDemand.getCategoryId(),
                restaurantDemand.getName(),
                authorGithubId,
                restaurantDemand.isWriter(githubId),
                restaurantDemand.isRegistered()
        );
    }
}

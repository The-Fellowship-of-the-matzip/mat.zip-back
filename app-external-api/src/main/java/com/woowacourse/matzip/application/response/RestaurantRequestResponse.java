package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import lombok.Getter;

@Getter
public class RestaurantRequestResponse {

    private Long id;
    private Long categoryId;
    private String name;
    private String author;
    private boolean updatable;
    private boolean registered;

    private RestaurantRequestResponse() {
    }

    public RestaurantRequestResponse(final Long id, final Long categoryId, final String name, final String author,
                                     final boolean updatable,
                                     final boolean registered) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.author = author;
        this.updatable = updatable;
        this.registered = registered;
    }

    public static RestaurantRequestResponse of(final RestaurantRequest restaurantRequest, final String githubId) {
        String authorGithubId = restaurantRequest.getMember().getGithubId();
        return new RestaurantRequestResponse(
                restaurantRequest.getId(),
                restaurantRequest.getCategoryId(),
                restaurantRequest.getName(),
                authorGithubId,
                authorGithubId.equals(githubId),
                restaurantRequest.isRegistered()
        );
    }
}

package com.woowacourse.matzip.application.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantCreateRequest {

    @NotNull
    private String name;
    @NotNull
    private String address;

    private long distance;
    @NotNull
    private String kakaoMapUrl;
    @NotNull
    private String imageUrl;

    private RestaurantCreateRequest() {
    }

    public RestaurantCreateRequest(final String name, final String address, final long distance,
                                   final String kakaoMapUrl, final String imageUrl) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.kakaoMapUrl = kakaoMapUrl;
        this.imageUrl = imageUrl;
    }
}

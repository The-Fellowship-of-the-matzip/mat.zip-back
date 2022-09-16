package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.category.Category;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantResponse {

    private final Long id;
    private final String categoryName;
    private final String campusName;
    private final String name;
    private final String address;
    private final Long distance;
    private final String kakaoMapUrl;
    private final String imageUrl;

    private RestaurantResponse(final Long id, final String categoryName, final String campusName, final String name,
                              final String address, final Long distance, final String kakaoMapUrl,
                              final String imageUrl) {
        this.id = id;
        this.categoryName = categoryName;
        this.campusName = campusName;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.kakaoMapUrl = kakaoMapUrl;
        this.imageUrl = imageUrl;
    }

    public static RestaurantResponse of(final Restaurant restaurant, final Category category, final Campus campus) {
        return new RestaurantResponse(
                restaurant.getId(),
                category.getName(),
                campus.getName(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getDistance(),
                restaurant.getKakaoMapUrl(),
                restaurant.getImageUrl()
        );
    }
}

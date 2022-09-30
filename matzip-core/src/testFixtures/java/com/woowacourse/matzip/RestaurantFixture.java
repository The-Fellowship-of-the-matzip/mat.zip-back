package com.woowacourse.matzip;

import com.woowacourse.matzip.domain.restaurant.Restaurant;

public enum RestaurantFixture {

    RESTAURANT_1("뽕나무쟁이 선릉본점", "서울 강남구 역삼로65길 31", 100L, "https://place.map.kakao.com/11190567", "image");

    private final String name;
    private final String address;
    private final Long distance;
    private final String kakaoMapUrl;
    private final String imageUrl;

    RestaurantFixture(final String name, final String address, final Long distance, final String kakaoMapUrl, final String imageUrl) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.kakaoMapUrl = kakaoMapUrl;
        this.imageUrl = imageUrl;
    }

    public Restaurant toRestaurantWithCategoryIdAndCampusId(final Long categoryId, final Long campusId) {
        return Restaurant.builder()
                .categoryId(categoryId)
                .campusId(campusId)
                .name(RESTAURANT_1.name)
                .address(RESTAURANT_1.address)
                .distance(RESTAURANT_1.distance)
                .kakaoMapUrl(RESTAURANT_1.kakaoMapUrl)
                .imageUrl(RESTAURANT_1.imageUrl)
                .build();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Long getDistance() {
        return distance;
    }

    public String getKakaoMapUrl() {
        return kakaoMapUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

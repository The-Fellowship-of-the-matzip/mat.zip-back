package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantResponse {

    private Long id;
    private String name;
    private String address;
    private long distance;
    private double rating;
    private String kakaoMapUrl;
    private String imageUrl;
    private int reviewCount;
    private boolean liked;

    private RestaurantResponse() {
    }

    private RestaurantResponse(final Long id, final String name, final String address, final long distance,
                               final double rating, final String kakaoMapUrl, final String imageUrl, final int reviewCount,
                               final boolean liked) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.rating = rating;
        this.kakaoMapUrl = kakaoMapUrl;
        this.imageUrl = imageUrl;
        this.reviewCount = reviewCount;
        this.liked = liked;
    }

    public static RestaurantResponse of(final Restaurant restaurant, final double rating, final boolean liked) {
        return new RestaurantResponse(restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getDistance(),
                rating,
                restaurant.getKakaoMapUrl(),
                restaurant.getImageUrl(),
                restaurant.getReviewCount(),
                liked);
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}

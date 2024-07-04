package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantTitleResponse {

    private Long id;
    private String name;
    private String address;
    private long distance;
    private double rating;
    private String imageUrl;
    private int reviewCount;
    private boolean liked;
    private int bookmarkCount;

    private RestaurantTitleResponse() {
    }

    public RestaurantTitleResponse(final Restaurant restaurant, final double rating, final boolean liked, final int bookmarkCount) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.distance = restaurant.getDistance();
        this.rating = rating;
        this.imageUrl = restaurant.getImageUrl();
        this.reviewCount = restaurant.getReviewCount();
        this.liked = liked;
        this.bookmarkCount = bookmarkCount;
    }
}

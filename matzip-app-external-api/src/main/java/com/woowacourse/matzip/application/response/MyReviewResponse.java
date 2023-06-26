package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.review.Review;
import lombok.Getter;

@Getter
public class MyReviewResponse {

    private Long id;
    private RestaurantShortInfo restaurant;
    private String content;
    private int rating;
    private String menu;
    private String imageUrl;
    private boolean updatable;

    private MyReviewResponse() {
    }

    public MyReviewResponse(final Long id,
                            final RestaurantShortInfo restaurant,
                            final String content,
                            final int rating,
                            final String menu,
                            final String imageUrl,
                            final boolean updatable) {
        this.id = id;
        this.restaurant = restaurant;
        this.content = content;
        this.rating = rating;
        this.menu = menu;
        this.imageUrl = imageUrl;
        this.updatable = updatable;
    }

    public static MyReviewResponse of(Review review, Restaurant restaurant) {
        return new MyReviewResponse(
                review.getId(),
                RestaurantShortInfo.from(restaurant),
                review.getContent(),
                review.getRating(),
                review.getMenu(),
                review.getImageUrl(),
                true
        );
    }

    @Getter
    public static class RestaurantShortInfo {
        private Long id;
        private String name;
        private String imageUrl;

        private RestaurantShortInfo() {
        }

        public RestaurantShortInfo(final Long id, final String name, final String imageUrl) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public static RestaurantShortInfo from(final Restaurant restaurant) {
            return new RestaurantShortInfo(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getImageUrl()
            );
        }
    }
}

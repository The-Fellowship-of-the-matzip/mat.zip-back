package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.review.ReviewCreateEvent;
import com.woowacourse.matzip.domain.review.ReviewDeleteEvent;
import com.woowacourse.matzip.domain.review.ReviewUpdateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Async(value = "asyncTaskExecutor")
public class RestaurantEventListener {

    private final RestaurantService restaurantService;

    public RestaurantEventListener(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @TransactionalEventListener
    public void handleReviewCreateEvent(final ReviewCreateEvent event) {
        Long restaurantId = event.getRestaurantId();
        int rating = event.getRating();
        restaurantService.updateWhenReviewCreate(restaurantId, rating);
    }

    @TransactionalEventListener
    public void handleReviewDeleteEvent(final ReviewDeleteEvent event) {
        Long restaurantId = event.getRestaurantId();
        int rating = event.getRating();
        restaurantService.updateWhenReviewDelete(restaurantId, rating);
    }

    @TransactionalEventListener
    public void handleReviewUpdateEvent(final ReviewUpdateEvent event) {
        Long restaurantId = event.getRestaurantId();
        int ratingGap = event.getRatingGap();
        restaurantService.updateWhenReviewUpdate(restaurantId, ratingGap);
    }
}

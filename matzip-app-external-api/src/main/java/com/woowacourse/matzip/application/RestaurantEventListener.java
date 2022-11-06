package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.ReviewCreateEvent;
import com.woowacourse.matzip.domain.review.ReviewDeleteEvent;
import com.woowacourse.matzip.domain.review.ReviewUpdateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class RestaurantEventListener {

    private final RestaurantRepository restaurantRepository;

    public RestaurantEventListener(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Async
    @Transactional
    @TransactionalEventListener
    public void updateRestaurantByReviewCreate(final ReviewCreateEvent event) {
        Long restaurantId = event.getRestaurantId();
        int rating = event.getRating();
        restaurantRepository.updateRestaurantByReviewInsert(restaurantId, rating);
    }

    @Async
    @Transactional
    @TransactionalEventListener
    public void updateRestaurantByReviewDelete(final ReviewDeleteEvent event) {
        Long restaurantId = event.getRestaurantId();
        int rating = event.getRating();
        restaurantRepository.updateRestaurantByReviewDelete(restaurantId, rating);
    }

    @Async
    @Transactional
    @TransactionalEventListener
    public void updateRestaurantByReviewUpdate(final ReviewUpdateEvent event) {
        Long restaurantId = event.getRestaurantId();
        int ratingGap = event.getRatingGap();
        restaurantRepository.updateRestaurantByReviewUpdate(restaurantId, ratingGap);
    }
}

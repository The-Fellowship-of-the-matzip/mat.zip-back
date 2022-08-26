package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.ReviewResponse;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.review.Review;
import com.woowacourse.matzip.repository.RestaurantRepository;
import com.woowacourse.matzip.repository.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public AdminReviewService(final ReviewRepository reviewRepository,
                              final RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<ReviewResponse> findAll() {
        return reviewRepository.findAll()
                .stream()
                .map(this::getOf)
                .collect(Collectors.toList());
    }

    private ReviewResponse getOf(final Review review) {
        Restaurant restaurant = restaurantRepository.findById(review.getRestaurantId())
                .orElse(null);
        return ReviewResponse.of(review, restaurant);
    }
}

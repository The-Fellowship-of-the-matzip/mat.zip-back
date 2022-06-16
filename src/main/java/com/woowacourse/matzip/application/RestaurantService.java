package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.RestaurantTitleResponse;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public RestaurantService(final RestaurantRepository restaurantRepository, final ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<RestaurantTitleResponse> findByCampusIdOrderByIdDesc(final Long campusId, final Long categoryId,
                                                                     final Pageable pageable) {
        return restaurantRepository.findPageByCampusIdOrderByIdDesc(campusId, categoryId, pageable)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private RestaurantTitleResponse toResponse(Restaurant restaurant) {
        Double rating = reviewRepository.findAverageRatingByRestaurantId(restaurant.getId());
        return RestaurantTitleResponse.of(restaurant, rating != null ? rating : 0.0);
    }
}

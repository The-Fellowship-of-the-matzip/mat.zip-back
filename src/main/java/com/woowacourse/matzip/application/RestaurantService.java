package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.RestaurantResponse;
import com.woowacourse.matzip.application.response.RestaurantTitleResponse;
import com.woowacourse.matzip.application.response.RestaurantTitlesResponse;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantFindQueryFactory;
import com.woowacourse.matzip.domain.restaurant.RestaurantReadOnlyRepository;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.restaurant.SortCondition;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.matzip.exception.RestaurantNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantReadOnlyRepository restaurantReadOnlyRepository;
    private final ReviewRepository reviewRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             RestaurantReadOnlyRepository restaurantReadOnlyRepository,
                             ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantReadOnlyRepository = restaurantReadOnlyRepository;
        this.reviewRepository = reviewRepository;
    }

    public RestaurantTitlesResponse findByCampusIdAndCategoryId(final String sortCondition, final Long campusId,
                                                                final Long categoryId, final Pageable pageable) {
        String query = RestaurantFindQueryFactory.from(sortCondition);
        Slice<Restaurant> page = restaurantReadOnlyRepository.findPageByCampusIdAndCategoryId(query, campusId,
                categoryId, pageable);
        return toRestaurantTitlesResponse(page);
    }

    private RestaurantTitlesResponse toRestaurantTitlesResponse(Slice<Restaurant> page) {
        List<RestaurantTitleResponse> restaurantTitleResponses = page.stream()
                .map(this::toResponseTitleResponse)
                .collect(Collectors.toList());
        return new RestaurantTitlesResponse(page.hasNext(), restaurantTitleResponses);
    }

    private RestaurantTitleResponse toResponseTitleResponse(final Restaurant restaurant) {
        double rating = reviewRepository.findAverageRatingUsingRestaurantId(restaurant.getId())
                .orElse(0.0);
        return RestaurantTitleResponse.of(restaurant, rating);
    }

    public List<RestaurantTitleResponse> findRandomsByCampusId(final Long campusId, final int size) {
        return restaurantRepository.findRandomsByCampusId(campusId, size)
                .stream()
                .map(this::toResponseTitleResponse)
                .collect(Collectors.toList());
    }

    public RestaurantResponse findById(final Long restaurantId) {
        return RestaurantResponse.of(
                restaurantRepository.findById(restaurantId)
                        .orElseThrow(RestaurantNotFoundException::new),
                reviewRepository.findAverageRatingUsingRestaurantId(restaurantId)
                        .orElse(0.0)
        );
    }

    public RestaurantTitlesResponse findTitlesByCampusIdAndNameContainingIgnoreCaseIdDescSort(final Long campusId,
                                                                                              final String name,
                                                                                              final Pageable pageable) {
        Pageable pageableById = toIdDescSortPageable(pageable);
        return toRestaurantTitlesResponse(
                restaurantRepository.findPageByCampusIdAndNameContainingIgnoreCase(campusId, name, pageableById)
        );
    }

    private Pageable toIdDescSortPageable(final Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), SortCondition.DEFAULT.getValue());
    }
}

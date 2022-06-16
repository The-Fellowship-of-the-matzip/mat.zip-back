package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.RestaurantTitleResponse;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantTitleResponse> findByCampusIdOrderByIdDesc(final Long campusId, final Long categoryId,
                                                                     final Pageable pageable) {
        return restaurantRepository.findPageByCampusIdOrderByIdDesc(campusId, categoryId, pageable)
                .stream()
                .map(restaurant -> RestaurantTitleResponse.of(restaurant, 0))
                .collect(Collectors.toList());
    }
}

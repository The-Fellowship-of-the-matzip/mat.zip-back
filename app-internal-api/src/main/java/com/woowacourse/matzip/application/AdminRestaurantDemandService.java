package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.RestaurantDemandResponse;
import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.category.Category;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import com.woowacourse.matzip.exception.CampusNotFoundException;
import com.woowacourse.matzip.exception.CategoryNotFoundException;
import com.woowacourse.matzip.repository.CampusRepository;
import com.woowacourse.matzip.repository.CategoryRepository;
import com.woowacourse.matzip.repository.RestaurantDemandRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminRestaurantDemandService {

    private final RestaurantDemandRepository restaurantDemandRepository;
    private final CategoryRepository categoryRepository;
    private final CampusRepository campusRepository;

    public AdminRestaurantDemandService(final RestaurantDemandRepository restaurantDemandRepository,
                                        final CategoryRepository categoryRepository,
                                        final CampusRepository campusRepository) {
        this.restaurantDemandRepository = restaurantDemandRepository;
        this.categoryRepository = categoryRepository;
        this.campusRepository = campusRepository;
    }

    public List<RestaurantDemandResponse> findAll() {
        return restaurantDemandRepository.findAll()
                .stream()
                .map(this::toRestaurantResponse)
                .collect(Collectors.toList());
    }

    private RestaurantDemandResponse toRestaurantResponse(final RestaurantRequest restaurantRequest) {
        Category category = categoryRepository.findById(restaurantRequest.getId())
                .orElseThrow(CategoryNotFoundException::new);
        Campus campus = campusRepository.findById(restaurantRequest.getCampusId())
                .orElseThrow(CampusNotFoundException::new);
        return RestaurantDemandResponse.of(restaurantRequest, category, campus);
    }
}

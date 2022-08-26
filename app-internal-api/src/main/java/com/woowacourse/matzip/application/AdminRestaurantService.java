package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.repository.RestaurantRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminRestaurantService {

    private final RestaurantRepository restaurantRepository;

    public AdminRestaurantService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
}

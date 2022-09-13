package com.woowacourse.matzip.repository;

import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDemandRepository extends JpaRepository<RestaurantRequest, Long> {
}

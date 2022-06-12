package com.woowacourse.matzip.domain.restaurant;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCampusId(Long campusId);
}

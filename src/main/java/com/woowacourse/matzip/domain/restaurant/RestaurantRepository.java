package com.woowacourse.matzip.domain.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Page<Restaurant> findByCampusIdOrderByIdDesc(Long campusId, Pageable pageable);
}

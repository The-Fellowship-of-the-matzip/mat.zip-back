package com.woowacourse.matzip.domain.restaurant;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRequestRepository extends JpaRepository<RestaurantRequest, Long> {

    Slice<RestaurantRequest> findPageByCampusId(Long campusId, Pageable pageable);
}

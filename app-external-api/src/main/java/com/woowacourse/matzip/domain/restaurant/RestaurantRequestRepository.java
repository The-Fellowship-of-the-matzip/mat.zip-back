package com.woowacourse.matzip.domain.restaurant;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRequestRepository extends JpaRepository<RestaurantDemand, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraphType.FETCH)
    Slice<RestaurantDemand> findPageByCampusIdOrderByCreatedAtDesc(Long campusId, Pageable pageable);
}

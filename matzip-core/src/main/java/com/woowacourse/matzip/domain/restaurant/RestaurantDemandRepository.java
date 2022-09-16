package com.woowacourse.matzip.domain.restaurant;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDemandRepository extends JpaRepository<RestaurantDemand, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraphType.FETCH)
    Slice<RestaurantDemand> findPageByCampusIdOrderByCreatedAtDesc(Long campusId, Pageable pageable);

    @EntityGraph(attributePaths = {"member"}, type = EntityGraphType.FETCH)
    List<RestaurantDemand> findAll();
}

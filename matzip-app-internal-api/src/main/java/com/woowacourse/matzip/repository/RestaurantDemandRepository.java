package com.woowacourse.matzip.repository;

import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDemandRepository extends JpaRepository<RestaurantDemand, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraphType.FETCH)
    List<RestaurantDemand> findAll();
}

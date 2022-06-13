package com.woowacourse.matzip.domain.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(
            value = "select r from Restaurant r "
                    + "where "
                    + "(:campusId = r.campusId)"
                    + "and "
                    + "(:categoryId is null or :categoryId = r.categoryId)"
                    + "order by r.id desc"
    )
    Page<Restaurant> findPageByCampusIdOrderByIdDesc(Long campusId, Long categoryId, Pageable pageable);
}

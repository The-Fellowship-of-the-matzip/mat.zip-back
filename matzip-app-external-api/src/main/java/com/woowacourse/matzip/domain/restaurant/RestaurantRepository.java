package com.woowacourse.matzip.domain.restaurant;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(
            value = "select id, category_id, campus_id, name, address, distance, kakao_map_url, image_url "
                    + "from restaurant "
                    + "where campus_id = ? "
                    + "order by rand() limit ?",
            nativeQuery = true
    )
    List<Restaurant> findRandomsByCampusId(Long campusId, int size);

    Slice<Restaurant> findPageByCampusIdAndNameContainingIgnoreCase(Long campusId, String name, Pageable pageable);
}

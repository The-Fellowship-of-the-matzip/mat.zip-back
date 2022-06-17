package com.woowacourse.matzip.domain.review;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(
            value = "select avg(r.rating) from Review r where (r.restaurantId = :restaurantId)"
    )
    Optional<Double> findAverageRatingByRestaurantId(Long restaurantId);

    List<Review> findReviewsByRestaurantIdOrderByIdDesc(Long restaurantId, Pageable pageable);
}

package com.woowacourse.matzip.domain.review;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends Repository<Review, Long>, ReviewDeleteRepository {

    Review save(Review review);

    Optional<Review> findById(Long reviewId);

    @Query(
            value = "select avg(r.rating) from Review r where (r.restaurantId = :restaurantId)"
    )
    Optional<Double> findAverageRatingUsingRestaurantId(@Param("restaurantId") Long restaurantId);

    @EntityGraph(attributePaths = {"member"}, type = EntityGraphType.FETCH)
    Slice<Review> findPageByRestaurantIdOrderByIdDesc(Long restaurantId, Pageable pageable);
}

package com.woowacourse.matzip.domain.review;

import java.util.Optional;

import com.woowacourse.matzip.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(
            value = "select avg(r.rating) from Review r where (r.restaurantId = :restaurantId)"
    )
    Optional<Double> findAverageRatingUsingRestaurantId(@Param("restaurantId") Long restaurantId);

    @EntityGraph(attributePaths = {"member"}, type = EntityGraphType.FETCH)
    Slice<Review> findPageByRestaurantIdOrderByIdDesc(Long restaurantId, Pageable pageable);

    Long countByMember(Member member);
}

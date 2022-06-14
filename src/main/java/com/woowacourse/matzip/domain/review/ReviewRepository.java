package com.woowacourse.matzip.domain.review;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findReviewsByRestaurantIdOrderByIdDesc(Long restaurantId, Pageable pageable);
}

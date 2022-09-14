package com.woowacourse.matzip.repository;

import com.woowacourse.matzip.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

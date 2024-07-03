package com.woowacourse.matzip.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewEventRepository extends JpaRepository<ReviewEvent, Long> {
}

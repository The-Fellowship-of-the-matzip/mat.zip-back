package com.woowacourse.matzip.repository;

import com.woowacourse.matzip.domain.campus.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Long> {
}

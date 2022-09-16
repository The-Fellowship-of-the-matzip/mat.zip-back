package com.woowacourse.matzip.repository;

import com.woowacourse.matzip.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

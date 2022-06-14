package com.woowacourse.matzip.application;

import static com.woowacourse.matzip.support.CategoryFixtures.양식;
import static com.woowacourse.matzip.support.CategoryFixtures.일식;
import static com.woowacourse.matzip.support.CategoryFixtures.중식;
import static com.woowacourse.matzip.support.CategoryFixtures.카페_디저트;
import static com.woowacourse.matzip.support.CategoryFixtures.한식;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.matzip.application.response.CategoryResponse;
import com.woowacourse.matzip.domain.category.CategoryRepository;
import com.woowacourse.support.SpringServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringServiceTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 카테고리_전체_목록을_반환한다() {
        categoryRepository.save(한식.toCategory());
        categoryRepository.save(중식.toCategory());
        categoryRepository.save(일식.toCategory());
        categoryRepository.save(양식.toCategory());
        categoryRepository.save(카페_디저트.toCategory());

        List<CategoryResponse> responses = categoryService.findAll();
        assertThat(responses).hasSize(5);
    }
}

package com.woowacourse.matzip.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.matzip.application.response.CategoryResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceTest extends ServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void 카테고리_전체_목록을_반환한다() {
        List<CategoryResponse> responses = categoryService.findAll();
        assertThat(responses).hasSize(5);
    }
}

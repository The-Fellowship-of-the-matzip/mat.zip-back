package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.category.Category;
import lombok.Getter;

@Getter
public class CategoryResponse {

    private Long id;
    private String name;

    private CategoryResponse() {
    }

    private CategoryResponse(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static CategoryResponse from(final Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}

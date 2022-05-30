package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.category.Category;
import lombok.Getter;

@Getter
public class CategoryResponse {

    private String name;

    private CategoryResponse() {
    }

    private CategoryResponse(final String name) {
        this.name = name;
    }

    public static CategoryResponse from(final Category category) {
        return new CategoryResponse(category.getName());
    }
}

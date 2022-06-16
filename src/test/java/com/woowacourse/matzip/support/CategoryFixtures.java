package com.woowacourse.matzip.support;

import com.woowacourse.matzip.domain.category.Category;

public enum CategoryFixtures {

    한식("한식"),
    중식("중식"),
    일식("일식"),
    양식("양식"),
    카페_디저트("카페/디저트"),
    ;

    private final String name;

    CategoryFixtures(final String name) {
        this.name = name;
    }

    public Category toCategory() {
        return Category.builder()
                .name(name)
                .build();
    }

    public String getName() {
        return name;
    }
}

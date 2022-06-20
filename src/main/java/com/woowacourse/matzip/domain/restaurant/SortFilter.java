package com.woowacourse.matzip.domain.restaurant;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortFilter {

    RATING(Sort.unsorted()),
    SPELL(Sort.by(asc("name"))),
    DEFAULT(Sort.by(desc("id")));

    private final Sort sortCondition;

    SortFilter(Sort sortCondition) {
        this.sortCondition = sortCondition;
    }
}

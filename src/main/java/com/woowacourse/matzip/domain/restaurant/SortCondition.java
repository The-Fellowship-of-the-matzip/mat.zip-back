package com.woowacourse.matzip.domain.restaurant;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

import com.woowacourse.matzip.exception.InvalidSortConditionException;
import java.util.Arrays;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortCondition {

    RATING("rating", Sort.by(desc("rating"))),
    SPELL("spell", Sort.by(asc("name"))),
    DEFAULT("default", Sort.by(desc("id"))),
    ;

    private final String key;
    private final Sort value;

    SortCondition(String key, Sort value) {
        this.key = key;
        this.value = value;
    }

    public static SortCondition from(String name) {
        return Arrays.stream(values())
                .filter(sortCondition -> sortCondition.getKey().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(InvalidSortConditionException::new);
    }
}

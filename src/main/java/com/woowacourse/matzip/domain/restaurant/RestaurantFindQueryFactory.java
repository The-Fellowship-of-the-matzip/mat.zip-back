package com.woowacourse.matzip.domain.restaurant;

import com.woowacourse.matzip.exception.InvalidSortConditionException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum RestaurantFindQueryFactory {

    ORDER_BY_RATING_DESC("RATING", "select r from Restaurant r left join Review rv on rv.restaurantId = r.id "
            + "where (r.campusId = :campusId) and (:categoryId is null or r.categoryId = :categoryId) "
            + "group by r.id order by avg(rv.rating) desc"),
    ORDER_BY_NAME_ASC("SPELL", "select r from Restaurant r "
            + "where (r.campusId = :campusId) and (:categoryId is null or r.categoryId = :categoryId) "
            + "order by r.name"),
    ORDER_BY_ID_DESC("DEFAULT", "select r from Restaurant r "
            + "where (r.campusId = :campusId) and (:categoryId is null or r.categoryId = :categoryId) "
            + "order by r.id desc"),
    ;

    private final String key;
    private final String query;

    RestaurantFindQueryFactory(String key, String query) {
        this.key = key;
        this.query = query;
    }

    public static String from(String sortCondition) {
        return Arrays.stream(values())
                .filter(condition -> condition.key.equalsIgnoreCase(sortCondition))
                .findAny()
                .orElseThrow(InvalidSortConditionException::new)
                .query;
    }
}

package com.woowacourse.matzip.infrastructure.restaurant;

import com.woowacourse.matzip.exception.InvalidSortConditionException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum RestaurantFindQueryFactory {

    ORDER_BY_RATING_DESC("RATING", "select r from Restaurant r "
            + "where r.campusId = :campusId and r.categoryId = :categoryId "
            + "order by r.reviewRatingAverage desc"),
    ORDER_BY_NAME_ASC("SPELL", "select r from Restaurant r "
            + "where r.campusId = :campusId and r.categoryId = :categoryId "
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
                .map(RestaurantFindQueryFactory::getQuery)
                .orElseThrow(InvalidSortConditionException::new);
    }
}

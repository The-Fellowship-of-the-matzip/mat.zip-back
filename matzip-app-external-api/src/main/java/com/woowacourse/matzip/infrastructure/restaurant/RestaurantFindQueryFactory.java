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
    ORDER_BY_DISTANCE_ASC("DISTANCE", """
            select r from Restaurant r
            where r.campusId = :campusId and r.categoryId = :categoryId
            order by r.distance
            """),
    ORDER_BY_BOOKMARK_COUNT_DESC("BOOKMARK", """
            select r from Restaurant r
            left join Bookmark b on b.restaurant = r
            where r.campusId = :campusId and r.categoryId = :categoryId
            group by r
            order by count(b) desc, r.name asc
            """),
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

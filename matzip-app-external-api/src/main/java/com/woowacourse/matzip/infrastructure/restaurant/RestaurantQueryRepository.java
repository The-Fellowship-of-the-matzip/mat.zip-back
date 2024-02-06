package com.woowacourse.matzip.infrastructure.restaurant;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantQueryRepository {

    public static final String CAMPUS_ID = "campusId";
    public static final String CATEGORY_ID = "categoryId";
    @PersistenceContext
    private EntityManager em;

    public Slice<Restaurant> findPageByCampusIdAndCategoryId(final String query, final Long campusId,
                                                             final Long categoryId, final Pageable pageable) {
        int startIndex = calculateStartIndex(pageable);
        int pageSize = pageable.getPageSize();
        List<Restaurant> restaurants = em.createQuery(query, Restaurant.class)
                .setParameter(CAMPUS_ID, campusId)
                .setParameter(CATEGORY_ID, categoryId)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize + 1)
                .getResultList();
        boolean hasNext = isHasNext(pageable, restaurants);
        if (hasNext) {
            restaurants = restaurants.subList(0, pageSize);
        }
        return new SliceImpl<>(restaurants, pageable, hasNext);
    }

    private int calculateStartIndex(Pageable pageable) {
        return pageable.getPageNumber() * pageable.getPageSize();
    }

    private boolean isHasNext(Pageable pageable, List<Restaurant> restaurants) {
        return restaurants.size() == pageable.getPageSize() + 1;
    }
}

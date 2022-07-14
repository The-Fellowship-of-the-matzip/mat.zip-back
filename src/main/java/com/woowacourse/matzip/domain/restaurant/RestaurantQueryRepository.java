package com.woowacourse.matzip.domain.restaurant;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantQueryRepository {

    @PersistenceContext
    private EntityManager em;

    public Slice<Restaurant> findPageByCampusIdAndCategoryId(String query, Long campusId, Long categoryId,
                                                             Pageable pageable) {
        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        List<Restaurant> restaurants = em.createQuery(query, Restaurant.class)
                .setParameter("campusId", campusId)
                .setParameter("categoryId", categoryId)
                .setFirstResult(startIndex)
                .setMaxResults(pageable.getPageSize() + 1)
                .getResultList();
        boolean hasNext = restaurants.size() == pageable.getPageSize() + 1;
        if (restaurants.size() > pageable.getPageSize()) {
            restaurants = restaurants.subList(0, pageable.getPageSize());
        }
        return new SliceImpl<>(restaurants, pageable, hasNext);
    }
}

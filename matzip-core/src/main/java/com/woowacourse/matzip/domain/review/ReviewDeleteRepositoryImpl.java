package com.woowacourse.matzip.domain.review;

import javax.persistence.EntityManager;
import org.springframework.util.Assert;

public class ReviewDeleteRepositoryImpl implements ReviewDeleteRepository {

    private final EntityManager em;

    public ReviewDeleteRepositoryImpl(final EntityManager em) {
        this.em = em;
    }

    @Override
    public void delete(final Review review) {
        Assert.notNull(review, "Entity must not be null!");
        if (review.getId() == null) {
            return;
        }
        Review existing = em.find(Review.class, review.getId());
        if (existing == null) {
            return;
        }
        review.readyForDelete();
        em.remove(em.contains(review) ? review : em.merge(review));
    }
}

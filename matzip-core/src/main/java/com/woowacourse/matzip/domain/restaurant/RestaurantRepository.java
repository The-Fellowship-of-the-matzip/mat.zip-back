package com.woowacourse.matzip.domain.restaurant;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(
            value = "select id, category_id, campus_id, name, address, distance, kakao_map_url, image_url, review_count, review_sum, review_avg, "
                    + "from restaurant "
                    + "where campus_id = ? "
                    + "order by rand() limit ?",
            nativeQuery = true
    )
    List<Restaurant> findRandomsByCampusId(Long campusId, int size);

    Slice<Restaurant> findPageByCampusIdAndNameContainingIgnoreCase(Long campusId, String name, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Restaurant r "
            + "set r.reviewRatingAverage = (r.reviewRatingSum + :rating) / cast((r.reviewCount + 1) as float), "
            + "r.reviewCount = r.reviewCount + 1, "
            + "r.reviewRatingSum = r.reviewRatingSum + :rating "
            + "where r.id = :id")
    void updateRestaurantByReviewInsert(Long id, long rating);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Restaurant r "
            + "set r.reviewRatingAverage = case r.reviewCount when 1 then 0 "
            + "else ((r.reviewRatingSum - :rating) / cast((r.reviewCount - 1) as float)) end , "
            + "r.reviewCount = r.reviewCount - 1, "
            + "r.reviewRatingSum = r.reviewRatingSum - :rating "
            + "where r.id = :id")
    void updateRestaurantByReviewDelete(Long id, long rating);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Restaurant r "
            + "set r.reviewRatingAverage = (r.reviewRatingSum + :ratingGap) / cast(r.reviewCount as float), "
            + "r.reviewRatingSum = r.reviewRatingSum + :ratingGap "
            + "where r.id = :id")
    void updateRestaurantByReviewUpdate(Long id, long ratingGap);
}

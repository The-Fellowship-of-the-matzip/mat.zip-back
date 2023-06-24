package com.woowacourse.matzip.domain.review;

import com.woowacourse.matzip.domain.member.Member;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(
            value = "select avg(r.rating) from Review r where (r.restaurantId = :restaurantId)"
    )
    Optional<Double> findAverageRatingUsingRestaurantId(@Param("restaurantId") Long restaurantId);

    @EntityGraph(attributePaths = {"member"}, type = EntityGraphType.FETCH)
    Slice<Review> findPageByRestaurantIdOrderByIdDesc(Long restaurantId, Pageable pageable);

    @Query(
            value = "select r.member.id as memberId, count(r.member.id) as reviewCount, avg(r.rating) as averageRating " +
                    "from Review r where r.member.id in :memberIds  group by r.member.id"
    )
    List<MemberReviewInfo> findMemberReviewInfosByMemberIds(List<Long> memberIds);

    Slice<Review> findPageByMemberOrderByIdDesc(Member member, Pageable pageable);
}

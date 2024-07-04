package com.woowacourse.matzip.domain.bookmark;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findBookmarkByMemberIdAndRestaurantId(Long memberId, Long restaurantId);

    void deleteBookmarkByMemberIdAndRestaurantId(Long memberId, Long restaurantId);

    int countByRestaurantId(Long restaurantId);
}

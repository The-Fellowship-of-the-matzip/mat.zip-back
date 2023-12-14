package com.woowacourse.matzip.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UnusedImageRepository extends JpaRepository<UnusedImage, Long> {

    void deleteByImageUrl(final String imageUrl);

    List<UnusedImage> findAllByCreatedAtBefore(final LocalDateTime date);

    @Modifying
    @Query("delete from UnusedImage ui where ui.createdAt < :date ")
    void deleteAllByCreatedAtBefore(final LocalDateTime date);
}

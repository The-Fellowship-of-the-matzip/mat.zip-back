package com.woowacourse.matzip.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UnusedImageRepository extends JpaRepository<UnusedImage, Long> {

    List<UnusedImage> findAllByCreatedAtBefore(final LocalDateTime date);

    @Modifying
    @Query("delete from UnusedImage i where i in :values")
    void deleteAllBy(final List<UnusedImage> values);
}

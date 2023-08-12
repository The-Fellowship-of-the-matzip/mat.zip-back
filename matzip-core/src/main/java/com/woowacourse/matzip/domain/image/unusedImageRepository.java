package com.woowacourse.matzip.domain.image;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface unusedImageRepository extends JpaRepository<UnusedImage, Long> {

    void deleteByKey(String key);

    List<UnusedImage> findAllByCreatedAtBefore(LocalDateTime date);

    void deleteAllByCreatedAtBefore(LocalDateTime date);
}

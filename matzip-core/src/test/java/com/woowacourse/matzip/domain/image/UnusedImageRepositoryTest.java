package com.woowacourse.matzip.domain.image;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaAuditing
class UnusedImageRepositoryTest {

    @Autowired
    private UnusedImageRepository unusedImageRepository;

    @Test
    void a() {
        unusedImageRepository.save(UnusedImage.builder()
                                              .imageUrl("213")
                                              .build());

        unusedImageRepository.deleteAllByCreatedAtBefore(LocalDate.now().atStartOfDay());

        List<UnusedImage> unusedImages = unusedImageRepository.findAll();

        assertThat(unusedImages).hasSize(1);
    }
}

package com.woowacourse.matzip.domain.campus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.exception.CampusNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CampusCacheRepositoryTest {

    private CampusCacheRepository campusCacheRepository;

    @BeforeEach
    void setUp() {
        campusCacheRepository = new CampusCacheRepository(List.of(new Campus(1L, "JAMSIL"), new Campus(2L, "SEOLLEUNG")));
    }

    @Test
    void 존재하지_않는_캠퍼스일때_예외발생() {
        assertThatThrownBy(() -> campusCacheRepository.findById(3L))
                .isInstanceOf(CampusNotFoundException.class)
                .hasMessage("존재하지 않는 캠퍼스입니다.");
    }

    @Test
    void 캠퍼스이름으로_캠퍼스_반환() {
        Campus campus = campusCacheRepository.findById(1L);
        assertThat(campus).isInstanceOf(Campus.class);
    }
}

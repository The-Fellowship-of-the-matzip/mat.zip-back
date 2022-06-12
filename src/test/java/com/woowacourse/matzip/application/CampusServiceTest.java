package com.woowacourse.matzip.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.matzip.application.response.CampusResponse;
import com.woowacourse.support.SpringServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringServiceTest
class CampusServiceTest {

    @Autowired
    private CampusService campusService;

    @Test
    void 캠퍼스_전체_목록을_반환한다() {
        List<CampusResponse> responses = campusService.findAll();
        assertThat(responses).hasSize(2);
    }
}

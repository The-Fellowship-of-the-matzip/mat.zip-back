package com.woowacourse.document;

import static com.woowacourse.document.DocumentationFixture.CAMPUS_RESPONSES;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class CampusDocumentation extends Documentation {

    @Test
    void 캠퍼스_전체조회() {
        when(campusService.findAll()).thenReturn(CAMPUS_RESPONSES);

        docsGiven
                .when().get("/api/campuses")
                .then().log().all()
                .apply(document("campuses/list"))
                .statusCode(HttpStatus.OK.value());
    }
}

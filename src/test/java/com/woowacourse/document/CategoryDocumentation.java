package com.woowacourse.document;

import static com.woowacourse.document.DocumentationFixture.CATEGORY_RESPONSES;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class CategoryDocumentation extends Documentation {

    @Test
    void 카테고리_전체조회() {
        when(categoryService.findAll()).thenReturn(CATEGORY_RESPONSES);

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/categories")
                .then().log().all()
                .apply(document("categories/list"))
                .statusCode(HttpStatus.OK.value());
    }
}

package com.woowacourse.document;

import static com.woowacourse.document.DocumentationFixture.CATEGORY_RESPONSES;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class CategoryDocumentation extends Documentation {

    @Test
    void 카테고리_전체조회() {
        when(categoryService.findAll()).thenReturn(CATEGORY_RESPONSES);

        docsGiven
                .when().get("/api/categories")
                .then().log().all()
                .apply(document("categories/list"))
                .statusCode(HttpStatus.OK.value());
    }
}

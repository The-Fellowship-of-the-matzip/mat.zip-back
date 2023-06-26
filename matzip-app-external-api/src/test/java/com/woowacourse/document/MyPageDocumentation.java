package com.woowacourse.document;

import static com.woowacourse.document.DocumentationFixture.MY_PROFILE_RESPONSE;
import static com.woowacourse.document.DocumentationFixture.MY_REVIEWS_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MyPageDocumentation extends Documentation {

    @Test
    void 내_정보를_조회한다() {
        when(myPageService.findProfile(any())).thenReturn(MY_PROFILE_RESPONSE);

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .when().get("/api/mypage/profile")
                .then().log().all()
                .apply(document("mypage/profile"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 내_리뷰를_조회한다() {
        when(myPageService.findPageByMember(any(), any()))
                .thenReturn(MY_REVIEWS_RESPONSE);

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .when().get("/api/mypage/reviews?page=0&size=5")
                .then().log().all()
                .apply(document("mypage/reviews"))
                .statusCode(HttpStatus.OK.value());
    }
}

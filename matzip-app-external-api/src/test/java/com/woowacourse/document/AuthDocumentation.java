package com.woowacourse.document;

import static com.woowacourse.matzip.MemberFixtures.HUNI;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.woowacourse.auth.application.dto.TokenResponse;
import com.woowacourse.auth.exception.GithubAccessException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AuthDocumentation extends Documentation {

    @Test
    void 로그인한다() {
        when(authService.createToken(anyString())).thenReturn(new TokenResponse(HUNI.getAccessToken()));

        docsGiven
                .when().get("/api/login?code=1")
                .then().log().all()
                .apply(document("auth/login",
                        responseFields(
                                fieldWithPath("accessToken").description("액세스 토큰")
                        )))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 로그인_실패_한다() {
        when(authService.createToken(anyString())).thenThrow(new GithubAccessException());

        docsGiven
                .when().get("/api/login?code=2")
                .then().log().all()
                .apply(document("auth/login-failed",
                        responseFields(
                                fieldWithPath("message").description("에러메시지")
                        )))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}

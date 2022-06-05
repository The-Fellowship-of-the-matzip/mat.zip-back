package com.woowacourse.acceptance;

import static com.woowacourse.acceptance.support.RestAssuredRequest.httpGetRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.auth.application.dto.TokenResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void code를_요청하면_토큰을_반환한다() {
        ExtractableResponse<Response> response = 로그인();
        토큰을_반환한다(response);
    }

    private ExtractableResponse<Response> 로그인() {
        return httpGetRequest("/api/login?code=1");
    }

    private void 토큰을_반환한다(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.as(TokenResponse.class).getAccessToken()).isNotNull()
        );
    }
}

package com.woowacourse.acceptance;

import static com.woowacourse.acceptance.support.RestAssuredRequest.httpGetRequest;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class CampusAcceptanceTest extends AcceptanceTest {

    private static ExtractableResponse<Response> 캠퍼스_전체_조회_요청() {
        return httpGetRequest("/api/campuses");
    }

    @Test
    void 캠퍼스_전체_조회() {
        ExtractableResponse<Response> response = 캠퍼스_전체_조회_요청();
        캠퍼스_조회에_성공한다(response);
    }

    private void 캠퍼스_조회에_성공한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

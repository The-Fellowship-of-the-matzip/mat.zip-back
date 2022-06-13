package com.woowacourse.acceptance;

import static com.woowacourse.acceptance.support.RestAssuredRequest.httpGetRequest;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RestaurantAcceptanceTest extends AcceptanceTest {

    private static final Long 선릉캠퍼스_ID = 2L;

    @Test
    void 선릉캠퍼스_식당_목록을_모두_조회한다() {
        ExtractableResponse<Response> response = 캠퍼스_식당_전체_조회_요청(선릉캠퍼스_ID);
        식당_조회에_성공한다(response);
    }

    private static ExtractableResponse<Response> 캠퍼스_식당_전체_조회_요청(final Long campusId) {
        return httpGetRequest("/api/campuses/" + campusId + "/restaurants");
    }

    private void 식당_조회에_성공한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

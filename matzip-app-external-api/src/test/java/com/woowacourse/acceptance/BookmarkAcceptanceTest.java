package com.woowacourse.acceptance;

import static com.woowacourse.acceptance.AuthAcceptanceTest.로그인_토큰;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpDeleteRequest;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpPostRequest;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class BookmarkAcceptanceTest extends AcceptanceTest {

    private static final Long 식당_ID_1 = 1L;
    private static final Long 식당_ID_2 = 2L;
    private static final Long 식당_ID_3 = 3L;

    public static ExtractableResponse<Response> 북마크_저장_요청(final Long restaurantId, final String accessToken) {
        return httpPostRequest("/api/restaurants/" + restaurantId + "/bookmarks", accessToken);
    }

    private static ExtractableResponse<Response> 북마크_삭제_요청(final Long restaurantId, final String accessToken) {
        return httpDeleteRequest("/api/restaurants/" + restaurantId + "/bookmarks", accessToken);
    }

    @Test
    void 북마크_저장_성공() {
        String accessToken = 로그인_토큰();

        ExtractableResponse<Response> response = 북마크_저장_요청(식당_ID_1, accessToken);
        북마크_저장에_성공한다(response);
    }

    @Test
    void 북마크_저장_실패_이미_등록된_북마크() {
        String accessToken = 로그인_토큰();
        북마크_저장_요청(식당_ID_2, accessToken);

        ExtractableResponse<Response> response = 북마크_저장_요청(식당_ID_2, accessToken);
        북마크_저장에_실패한다(response);
    }

    @Test
    void 북마크_삭제_성공() {
        String accessToken = 로그인_토큰();
        북마크_저장_요청(식당_ID_1, accessToken);

        ExtractableResponse<Response> response = 북마크_삭제_요청(식당_ID_1, accessToken);
        북마크_삭제에_성공한다(response);
    }

    private void 북마크_저장에_성공한다(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private void 북마크_저장에_실패한다(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private void 북마크_삭제에_성공한다(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}

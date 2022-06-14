package com.woowacourse.acceptance;

import static com.woowacourse.acceptance.AuthAcceptanceTest.로그인_토큰;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpGetRequest;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpPostRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.application.response.ReviewResponse;
import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ReviewAcceptanceTest extends AcceptanceTest {

    private static final Long 식당_ID = 1L;

    private static ExtractableResponse<Response> 리뷰_생성_요청(final Long restaurantId, final String accessToken,
                                                          final ReviewCreateRequest request) {
        return httpPostRequest("/api/restaurants/" + restaurantId + "/reviews", accessToken, request);
    }

    private static ExtractableResponse<Response> 리뷰_조회_요청(final Long restaurantId, final int page, final int size) {
        return httpGetRequest("/api/restaurants/" + restaurantId + "/reviews?page=" + page + "&size=" + size);
    }

    @Test
    void 리뷰_작성() {
        String accessToken = 로그인_토큰();
        ReviewCreateRequest request = new ReviewCreateRequest("맛있네요.", 4, "무닭볶음탕 (중)");

        ExtractableResponse<Response> response = 리뷰_생성_요청(식당_ID, accessToken, request);
        리뷰_작성에_성공한다(response);
    }

    private void 리뷰_작성에_성공한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 리뷰_조회() {
        for (int i = 1; i <= 20; i++) {
            리뷰_작성();
        }
        ExtractableResponse<Response> response = 리뷰_조회_요청(식당_ID, 2, 5);
        리뷰_조회에_성공한다(response);
    }

    private void 리뷰_조회에_성공한다(final ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList(".", ReviewResponse.class)).hasSize(5)
        );
    }
}

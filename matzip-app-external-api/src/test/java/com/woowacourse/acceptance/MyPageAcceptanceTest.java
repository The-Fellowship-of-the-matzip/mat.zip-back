package com.woowacourse.acceptance;


import static com.woowacourse.acceptance.AuthAcceptanceTest.로그인_토큰;
import static com.woowacourse.acceptance.ReviewAcceptanceTest.리뷰_생성_요청;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpGetRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MyPageAcceptanceTest extends AcceptanceTest {

    private static final Long 식당_ID = 1L;

    private static ExtractableResponse<Response> 내_리뷰_조회_요청(final String accessToken,
                                                            final int page,
                                                            final int size) {
        return httpGetRequest("/api/mypage" + "/reviews?page=" + page + "&size=" + size, accessToken);
    }

    @Test
    void 내_리뷰_조회() {
        for (int i = 1; i <= 20; i++) {
            리뷰_작성();
        }

        String accessToken = 로그인_토큰();

        var response = 내_리뷰_조회_요청(accessToken, 2, 5);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getBoolean("hasNext")).isTrue(),
                () -> assertThat(response.jsonPath().getList("reviews")).hasSize(5)
        );
    }

    private void 리뷰_작성() {
        String accessToken = 로그인_토큰();
        ReviewCreateRequest request = new ReviewCreateRequest("맛있네요.", 4, "무닭볶음탕 (중)");

        리뷰_생성_요청(식당_ID, accessToken, request);
    }
}

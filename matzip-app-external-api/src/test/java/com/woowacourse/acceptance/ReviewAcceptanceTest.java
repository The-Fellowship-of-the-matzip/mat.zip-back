package com.woowacourse.acceptance;

import static com.woowacourse.acceptance.AuthAcceptanceTest.로그인_토큰;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpDeleteRequest;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpGetRequest;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpPostRequest;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpPutRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.auth.application.dto.TokenResponse;
import com.woowacourse.matzip.application.response.ReviewResponse;
import com.woowacourse.matzip.application.response.ReviewsResponse;
import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import com.woowacourse.matzip.presentation.request.ReviewUpdateRequest;
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

    private static ExtractableResponse<Response> 리뷰_조회_요청(final Long restaurantId, final String accessToken,
                                                          final int page, final int size) {
        return httpGetRequest("/api/restaurants/" + restaurantId + "/reviews?page=" + page + "&size=" + size,
                accessToken);
    }

    private static ExtractableResponse<Response> 리뷰_수정_요청(final Long restaurantId, final Long reviewId,
                                                          final String accessToken,
                                                          final ReviewUpdateRequest reviewUpdateRequest) {
        return httpPutRequest("/api/restaurants/" + restaurantId + "/reviews/" + reviewId, accessToken,
                reviewUpdateRequest);
    }

    private static ExtractableResponse<Response> 리뷰_삭제_요청(final Long restaurantId, final Long reviewId,
                                                          final String accessToken) {
        return httpDeleteRequest("/api/restaurants/" + restaurantId + "/reviews/" + reviewId, accessToken);
    }

    @Test
    void 리뷰_작성() {
        String accessToken = 로그인_토큰();
        ReviewCreateRequest request = new ReviewCreateRequest("맛있네요.", 4, "무닭볶음탕 (중)");

        ExtractableResponse<Response> response = 리뷰_생성_요청(식당_ID, accessToken, request);
        리뷰_작성에_성공한다(response);
    }

    @Test
    void 리뷰_작성_시_null_리뷰() {
        String accessToken = 로그인_토큰();
        ReviewCreateRequest request = new ReviewCreateRequest(null, 4, "무닭볶음탕 (중)");

        ExtractableResponse<Response> response = 리뷰_생성_요청(식당_ID, accessToken, request);
        리뷰_작성에_실패한다(response);
    }

    @Test
    void 리뷰_작성_시_null_메뉴() {
        String accessToken = 로그인_토큰();
        ReviewCreateRequest request = new ReviewCreateRequest("맛있네요.", 4, null);

        ExtractableResponse<Response> response = 리뷰_생성_요청(식당_ID, accessToken, request);
        리뷰_작성에_실패한다(response);
    }

    @Test
    void 리뷰_조회() {
        for (int i = 1; i <= 20; i++) {
            리뷰_작성();
        }
        ExtractableResponse<Response> response = 리뷰_조회_요청(식당_ID, 2, 5);
        리뷰_조회에_성공한다(response);
    }

    @Test
    void 리뷰_로그인_조회() {
        String accessToken = 로그인_토큰();
        String other = httpGetRequest("/api/login?code=2").as(TokenResponse.class).getAccessToken();
        for (int i = 1; i <= 20; i++) {
            ReviewCreateRequest request = new ReviewCreateRequest("맛있네요.", 4, "무닭볶음탕 (중)");
            리뷰_생성_요청(식당_ID, accessToken, request);
        }
        ReviewCreateRequest request = new ReviewCreateRequest("맛있네요.", 4, "무닭볶음탕 (중)");
        리뷰_생성_요청(식당_ID, other, request);
        ExtractableResponse<Response> response = 리뷰_조회_요청(식당_ID, accessToken, 0, 5);
        내_리뷰정보도_조회에_성공한다(response);
    }

    @Test
    void 리뷰_수정() {
        String accessToken = 로그인_토큰();
        ReviewCreateRequest request = new ReviewCreateRequest("맛있네요.", 4, "무닭볶음탕 (중)");

        리뷰_생성_요청(식당_ID, accessToken, request);
        ReviewResponse reviewResponse = 리뷰_조회_요청(식당_ID, accessToken, 0, 1)
                .as(ReviewsResponse.class)
                .getReviews()
                .get(0);

        ExtractableResponse<Response> response = 리뷰_수정_요청(식당_ID,
                reviewResponse.getId(),
                accessToken,
                new ReviewUpdateRequest("맛이 있어요.", 5, "무닭볶음탕 (대)"));

        ReviewResponse updatedResponse = 리뷰_조회_요청(식당_ID, accessToken, 0, 1)
                .as(ReviewsResponse.class)
                .getReviews()
                .get(0);

        리뷰가_수정됨(response, updatedResponse);
    }

    @Test
    void 리뷰가_삭제됨() {
        String accessToken = 로그인_토큰();
        ReviewCreateRequest request = new ReviewCreateRequest("맛있네요.", 4, "무닭볶음탕 (중)");

        리뷰_생성_요청(식당_ID, accessToken, request);
        ReviewResponse reviewResponse = 리뷰_조회_요청(식당_ID, accessToken, 0, 1)
                .as(ReviewsResponse.class)
                .getReviews()
                .get(0);

        ExtractableResponse<Response> response = 리뷰_삭제_요청(식당_ID, reviewResponse.getId(), accessToken);
        리뷰_삭제에_성공한다(response);
    }

    @Test
    void 리뷰_작성개수_조회() {
        for (int i = 1; i <= 10; i++) {
            리뷰_작성();
        }
        ExtractableResponse<Response> response = 리뷰_조회_요청(1L, 0, 1);
        사용자의_리뷰개수가_일치한다(response, 10L);
    }

    @Test
    void 리뷰_평균_별점_조회() {
        for (int i = 1; i <= 10; i++) {
            리뷰_작성();
        }
        ExtractableResponse<Response> response = 리뷰_조회_요청(1L, 0, 1);
        사용자의_평균별점이_일치한다(response, 4d);
    }

    private void 리뷰_작성에_성공한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private void 리뷰_삭제에_성공한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private void 리뷰_작성에_실패한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private void 리뷰_조회에_성공한다(final ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getObject(".", ReviewsResponse.class).getReviews()).hasSize(5)
        );
    }

    private void 내_리뷰정보도_조회에_성공한다(final ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getObject(".", ReviewsResponse.class).getReviews())
                        .extracting("updatable")
                        .containsExactly(false, true, true, true, true)
        );
    }

    private void 리뷰가_수정됨(final ExtractableResponse<Response> response, final ReviewResponse updatedResponse) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(updatedResponse.getContent()).isEqualTo("맛이 있어요."),
                () -> assertThat(updatedResponse.getRating()).isEqualTo(5),
                () -> assertThat(updatedResponse.getMenu()).isEqualTo("무닭볶음탕 (대)")
        );
    }

    private void 사용자의_리뷰개수가_일치한다(final ExtractableResponse<Response> response, final Long expectedReviewCount) {
        ReviewsResponse reviewsResponse = response.as(ReviewsResponse.class);
        assertThat(reviewsResponse.getReviews().get(0).getAuthor().getReviewCount()).isEqualTo(expectedReviewCount);
    }

    private void 사용자의_평균별점이_일치한다(final ExtractableResponse<Response> response, final Double expectedRating) {
        ReviewsResponse reviewsResponse = response.as(ReviewsResponse.class);
        assertThat(reviewsResponse.getReviews().get(0).getAuthor().getAverageRating()).isEqualTo(expectedRating);
    }
}

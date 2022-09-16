package com.woowacourse.acceptance;

import static com.woowacourse.acceptance.AuthAcceptanceTest.로그인_토큰;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpDeleteRequest;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpGetRequest;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpPostRequest;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpPutRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.application.response.RestaurantDemandResponse;
import com.woowacourse.matzip.application.response.RestaurantDemandsResponse;
import com.woowacourse.matzip.presentation.request.RestaurantDemandCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantDemandUpdateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class RestaurantDemandAcceptanceTest extends AcceptanceTest {

    private static ExtractableResponse<Response> 식당_추가_요청_생성_요청(final Long campusId, final String accessToken,
                                                                final RestaurantDemandCreateRequest request) {
        return httpPostRequest("/api/campuses/" + campusId + "/restaurantDemands", accessToken, request);
    }

    private static ExtractableResponse<Response> 식당_추가_요청_조회_요청(final Long campusId, final int page,
                                                                final int size) {
        return httpGetRequest("/api/campuses/" + campusId + "/restaurantDemands?page=" + page + "&size=" + size);
    }

    private static ExtractableResponse<Response> 식당_추가_요청_조회_요청(final Long campusId, final String accessToken,
                                                                final int page, final int size) {
        return httpGetRequest("/api/campuses/" + campusId + "/restaurantDemands?page=" + page + "&size=" + size,
                accessToken);
    }

    private static ExtractableResponse<Response> 식당_추가_요청_수정_요청(final Long campusId, final Long requestId,
                                                                final String accessToken,
                                                                final RestaurantDemandUpdateRequest reviewUpdateRequest) {
        return httpPutRequest("/api/campuses/" + campusId + "/restaurantDemands/" + requestId, accessToken,
                reviewUpdateRequest);
    }

    private static ExtractableResponse<Response> 식당_추가_요청_삭제_요청(final Long campusId, final Long requestId,
                                                                final String accessToken) {
        return httpDeleteRequest("/api/campuses/" + campusId + "/restaurantDemands/" + requestId, accessToken);
    }

    @Test
    void 식당_추가_요청_작성() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest = new RestaurantDemandCreateRequest(1L, "추가식당");

        ExtractableResponse<Response> response = 식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest);

        식당_추가_요청_작성에_성공한다(response);
    }

    @Test
    void 식당_추가_요청_작성_실패_카테고리_id_1미만() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest = new RestaurantDemandCreateRequest(0L, "추가식당");

        ExtractableResponse<Response> response = 식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest);

        식당_추가_요청_작성에_실패한다(response);
    }

    @Test
    void 식당_추가_요청_작성_실패_내용_null() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest = new RestaurantDemandCreateRequest(1L, null);

        ExtractableResponse<Response> response = 식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest);

        식당_추가_요청_작성에_실패한다(response);
    }

    @Test
    void 식당_추가_요청_목록_조회() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest1 = new RestaurantDemandCreateRequest(1L,
                "추가식당1");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest1);
        RestaurantDemandCreateRequest restaurantDemandCreateRequest2 = new RestaurantDemandCreateRequest(1L,
                "추가식당2");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest2);

        ExtractableResponse<Response> response = 식당_추가_요청_조회_요청(1L, 0, 2);

        식당_추가_요청_조회에_성공한다(response);
    }

    @Test
    void 식당_추가_요청_목록_로그인_조회() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest1 = new RestaurantDemandCreateRequest(1L,
                "추가식당1");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest1);
        RestaurantDemandCreateRequest restaurantDemandCreateRequest2 = new RestaurantDemandCreateRequest(1L,
                "추가식당2");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest2);

        ExtractableResponse<Response> response = 식당_추가_요청_조회_요청(1L, accessToken, 0, 2);

        내가_생성한_요청인지_조회도_성공한다(response);
    }

    @Test
    void 식당_추가_요청_수정() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest = new RestaurantDemandCreateRequest(1L, "추가식당");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest);

        Long requestId = 식당_추가_요청_조회_요청(1L, 0, 1)
                .as(RestaurantDemandsResponse.class)
                .getItems()
                .get(0)
                .getId();

        RestaurantDemandUpdateRequest restaurantDemandUpdateRequest = new RestaurantDemandUpdateRequest(1L, "수정된식당");
        ExtractableResponse<Response> response = 식당_추가_요청_수정_요청(1L, requestId, accessToken,
                restaurantDemandUpdateRequest);

        RestaurantDemandResponse updated = 식당_추가_요청_조회_요청(1L, 0, 1)
                .as(RestaurantDemandsResponse.class)
                .getItems()
                .get(0);

        식당_추가_요청이_수정에_성공한다(response, restaurantDemandUpdateRequest, updated);
    }

    @Test
    void 식당_추가_요청_수정_실패_작성자_아님() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest = new RestaurantDemandCreateRequest(1L, "추가식당");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest);

        Long requestId = 식당_추가_요청_조회_요청(1L, 0, 1)
                .as(RestaurantDemandsResponse.class)
                .getItems()
                .get(0)
                .getId();

        RestaurantDemandUpdateRequest restaurantDemandUpdateRequest = new RestaurantDemandUpdateRequest(1L, "수정된식당");
        ExtractableResponse<Response> response = 식당_추가_요청_수정_요청(1L, requestId, "",
                restaurantDemandUpdateRequest);

        식당_추가_요청_수정_인가에_실패한다(response);
    }

    @Test
    void 식당_추가_요청_수정_실패_카테고리_1미만() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest = new RestaurantDemandCreateRequest(1L, "추가식당");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest);

        Long requestId = 식당_추가_요청_조회_요청(1L, 0, 1)
                .as(RestaurantDemandsResponse.class)
                .getItems()
                .get(0)
                .getId();

        RestaurantDemandUpdateRequest restaurantDemandUpdateRequest = new RestaurantDemandUpdateRequest(0L, "수정된식당");
        ExtractableResponse<Response> response = 식당_추가_요청_수정_요청(1L, requestId, accessToken,
                restaurantDemandUpdateRequest);

        식당_추가_요청_수정에_실패한다(response);
    }

    @Test
    void 식당_추가_요청_수정_실패_이름_null() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest = new RestaurantDemandCreateRequest(1L, "추가식당");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest);

        Long requestId = 식당_추가_요청_조회_요청(1L, 0, 1)
                .as(RestaurantDemandsResponse.class)
                .getItems()
                .get(0)
                .getId();

        RestaurantDemandUpdateRequest restaurantDemandUpdateRequest = new RestaurantDemandUpdateRequest(1L, null);
        ExtractableResponse<Response> response = 식당_추가_요청_수정_요청(1L, requestId, accessToken,
                restaurantDemandUpdateRequest);

        식당_추가_요청_수정에_실패한다(response);
    }

    @Test
    void 식당_추가_요청_삭제() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest = new RestaurantDemandCreateRequest(1L, "추가식당");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest);

        Long requestId = 식당_추가_요청_조회_요청(1L, 0, 1)
                .as(RestaurantDemandsResponse.class)
                .getItems()
                .get(0)
                .getId();

        ExtractableResponse<Response> response = 식당_추가_요청_삭제_요청(1L, requestId, accessToken);

        식당_추가_요청_삭제에_성공한다(response);
    }

    @Test
    void 식당_추가_요청_삭제_실패_작성자_아님() {
        String accessToken = 로그인_토큰();
        RestaurantDemandCreateRequest restaurantDemandCreateRequest = new RestaurantDemandCreateRequest(1L, "추가식당");
        식당_추가_요청_생성_요청(1L, accessToken, restaurantDemandCreateRequest);

        Long requestId = 식당_추가_요청_조회_요청(1L, 0, 1)
                .as(RestaurantDemandsResponse.class)
                .getItems()
                .get(0)
                .getId();

        ExtractableResponse<Response> response = 식당_추가_요청_삭제_요청(1L, requestId, "");

        식당_추가_요청_삭제_인가에_실패한다(response);
    }

    private void 식당_추가_요청_작성에_성공한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private void 식당_추가_요청_삭제에_성공한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private void 식당_추가_요청_작성에_실패한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private void 식당_추가_요청_조회에_성공한다(final ExtractableResponse<Response> response) {
        List<RestaurantDemandResponse> items = response.as(RestaurantDemandsResponse.class)
                .getItems();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(items).hasSize(2),
                () -> assertThat(items).extracting("registered")
                        .containsOnly(false),
                () -> assertThat(items).extracting("updatable")
                        .containsOnly(false)
        );
    }

    private void 내가_생성한_요청인지_조회도_성공한다(final ExtractableResponse<Response> response) {
        List<RestaurantDemandResponse> items = response.as(RestaurantDemandsResponse.class)
                .getItems();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(items).hasSize(2),
                () -> assertThat(items).extracting("registered")
                        .containsOnly(false),
                () -> assertThat(items).extracting("updatable")
                        .containsOnly(true)
        );
    }

    private void 식당_추가_요청이_수정에_성공한다(final ExtractableResponse<Response> response,
                                    final RestaurantDemandUpdateRequest updateRequest,
                                    final RestaurantDemandResponse updatedResponse) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(updatedResponse.getName()).isEqualTo(updateRequest.getName())
        );
    }

    private void 식당_추가_요청_수정_인가에_실패한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private void 식당_추가_요청_삭제_인가에_실패한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private void 식당_추가_요청_수정에_실패한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}

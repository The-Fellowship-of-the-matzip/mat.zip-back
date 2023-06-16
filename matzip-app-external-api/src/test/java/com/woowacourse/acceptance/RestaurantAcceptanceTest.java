package com.woowacourse.acceptance;

import static com.woowacourse.acceptance.AuthAcceptanceTest.로그인_토큰;
import static com.woowacourse.acceptance.BookmarkAcceptanceTest.북마크_저장_요청;
import static com.woowacourse.acceptance.support.RestAssuredRequest.httpGetRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.application.response.RestaurantTitlesResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class RestaurantAcceptanceTest extends AcceptanceTest {

    private static final Long 선릉캠퍼스_ID = 2L;
    private static final Long 한식_ID = 1L;
    private static final Long 식당_1_ID = 1L;
    private static final Long 식당_2_ID = 2L;

    private static ExtractableResponse<Response> 캠퍼스_식당_페이지_조회_요청(final Long campusId, final int page, final int size) {
        return httpGetRequest("/api/campuses/" + campusId + "/restaurants?page=" + page + "&size=" + size);
    }

    private static ExtractableResponse<Response> 캠퍼스_식당_페이지_정렬_기준_변경해서_조회_요청(final Long campusId, final Long categoryId,
                                                                             final int page,
                                                                             final int size, final String filter) {
        return httpGetRequest(
                "/api/campuses/" + campusId + "/restaurants?page=" + page + "&size=" + size + "&filter=" + filter
                        + "&categoryId=" + categoryId);
    }

    private static ExtractableResponse<Response> 캠퍼스_카테고리_식당_0페이지_조회_요청(Long campusId, Long categoryId) {
        return httpGetRequest(
                "/api/campuses/" + campusId + "/restaurants?categoryId=" + categoryId + "&page=0&size=10");
    }

    private static ExtractableResponse<Response> 캠퍼스_식당_무작위_조회_요청(Long campusId, int size) {
        return httpGetRequest("/api/campuses/" + campusId + "/restaurants/random?size=" + size);
    }

    private static ExtractableResponse<Response> 식당_조회_요청(Long restaurantId) {
        return httpGetRequest("/api/restaurants/" + restaurantId);
    }

    private static ExtractableResponse<Response> 식당_검색_요청(final Long campusId, final int page, final int size,
                                                          final String name) {
        return httpGetRequest(
                "/api/campuses/" + campusId + "/restaurants/search?page=" + page + "&size=" + size + "&name=" + name);
    }

    private static ExtractableResponse<Response> 북마크_된_식당_목록_검색_요청(final String accessToken) {
        return httpGetRequest("/api/restaurants/bookmarks", accessToken);
    }

    @Test
    void 선릉캠퍼스_식당_목록의_0페이지를_조회하고_다음_페이지가_있는지_확인한다() {
        ExtractableResponse<Response> response = 캠퍼스_식당_페이지_조회_요청(선릉캠퍼스_ID, 0, 1);
        식당_목록의_다음_페이지가_있다(response);
    }

    @Test
    void 선릉캠퍼스_식당_목록의_0페이지를_이름_순으로_조회한다() {
        ExtractableResponse<Response> response = 캠퍼스_식당_페이지_정렬_기준_변경해서_조회_요청(선릉캠퍼스_ID, 2L, 1, 2, "spell");
        식당이_정렬되어_있다(response, "마담밍3");
    }

    @Test
    void 선릉캠퍼스_한식_식당_목록의_0페이지를_조회한다() {
        ExtractableResponse<Response> response = 캠퍼스_카테고리_식당_0페이지_조회_요청(선릉캠퍼스_ID, 한식_ID);
        식당_조회에_성공한다(response);
    }

    @Test
    void 선릉캠퍼스_식당_목록을_2개_무작위로_조회한다() {
        ExtractableResponse<Response> response = 캠퍼스_식당_무작위_조회_요청(선릉캠퍼스_ID, 2);
        식당_조회에_성공한다(response);
    }

    @Test
    void 선릉캠퍼스_식당_1의_상세_정보를_조회한다() {
        ExtractableResponse<Response> response = 식당_조회_요청(식당_1_ID);
        식당_조회에_성공한다(response);
    }

    @Test
    void 선릉캠퍼스_식당을_이름으로_검색한다() {
        ExtractableResponse<Response> response = 식당_검색_요청(선릉캠퍼스_ID, 0, 2, "마담밍");
        식당_검색에_성공한다(response, "마담밍3", "마담밍2");
    }

    @Test
    void 북마크_된_식당_목록을_조회한다() {
        String accessToken = 로그인_토큰();
        북마크_저장_요청(식당_1_ID, accessToken);
        북마크_저장_요청(식당_2_ID, accessToken);

        ExtractableResponse<Response> response = 북마크_된_식당_목록_검색_요청(accessToken);
        식당_조회에_성공한다(response);
    }

    private void 식당_조회에_성공한다(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 식당_목록의_다음_페이지가_있다(final ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.as(RestaurantTitlesResponse.class).isHasNext()).isTrue()
        );
    }

    private void 식당이_정렬되어_있다(final ExtractableResponse<Response> response, String... names) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.as(RestaurantTitlesResponse.class).getRestaurants()).extracting("name")
                        .containsExactly(names)
        );
    }

    private void 식당_검색에_성공한다(final ExtractableResponse<Response> response, String... names) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.as(RestaurantTitlesResponse.class).getRestaurants()).extracting("name")
                        .containsExactly(names),
                () -> assertThat(response.as(RestaurantTitlesResponse.class).isHasNext()).isTrue()
        );
    }
}

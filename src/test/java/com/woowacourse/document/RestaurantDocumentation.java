package com.woowacourse.document;

import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_KOREAN_RESTAURANT_RESPONSES;
import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_RESTAURANT_RANDOM_2_RESPONSES;
import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_RESTAURANT_RESPONSES;
import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_RESTAURANT_RESPONSE_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class RestaurantDocumentation extends Documentation {

    @Test
    void 선릉캠퍼스_식당_목록의_0페이지를_조회한다() {
        when(restaurantService.findByCampusId(eq(2L), eq(null), any())).thenReturn(
                SEOLLEUNG_RESTAURANT_RESPONSES);

        docsGiven
                .when().get("/api/campuses/2/restaurants?page=0&size=10")
                .then().log().all()
                .apply(document("restaurants/list"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 선릉캠퍼스_한식_식당_목록의_0페이지를_조회한다() {
        when(restaurantService.findByCampusId(eq(2L), eq(1L), any())).thenReturn(
                SEOLLEUNG_KOREAN_RESTAURANT_RESPONSES);

        docsGiven
                .when().get("/api/campuses/2/restaurants?categoryId=1&page=0&size=10")
                .then().log().all()
                .apply(document("restaurants/list-category"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 선릉캠퍼스_식당_목록을_2개_무작위로_조회한다() {
        when(restaurantService.findRandomsByCampusId(eq(2L), eq(2))).thenReturn(
                SEOLLEUNG_RESTAURANT_RANDOM_2_RESPONSES);

        docsGiven
                .when().get("/api/campuses/2/restaurants/random?size=2")
                .then().log().all()
                .apply(document("restaurants/list-random"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 선릉캠퍼스_식당_1의_상세_정보를_조회한다() {
        when(restaurantService.findById(eq(1L))).thenReturn(SEOLLEUNG_RESTAURANT_RESPONSE_1);

        docsGiven
                .when().get("/api/restaurants/1")
                .then().log().all()
                .apply(document("restaurants"))
                .statusCode(HttpStatus.OK.value());
    }
}

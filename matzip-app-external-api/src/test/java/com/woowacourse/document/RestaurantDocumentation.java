package com.woowacourse.document;

import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_KOREAN_RESTAURANTS_RESPONSE;
import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_PORT_CUTLET_RESTAURANTS_RESPONSE;
import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_RESTAURANTS_RANDOM_2_RESPONSE;
import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_RESTAURANTS_RESPONSE;
import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_RESTAURANTS_SORT_BY_RATING_RESPONSE;
import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_RESTAURANTS_SORT_BY_SPELL_RESPONSE;
import static com.woowacourse.document.DocumentationFixture.SEOLLEUNG_RESTAURANT_RESPONSE_1;
import static com.woowacourse.matzip.infrastructure.restaurant.RestaurantFindQueryFactory.ORDER_BY_ID_DESC;
import static com.woowacourse.matzip.infrastructure.restaurant.RestaurantFindQueryFactory.ORDER_BY_NAME_ASC;
import static com.woowacourse.matzip.infrastructure.restaurant.RestaurantFindQueryFactory.ORDER_BY_RATING_DESC;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

public class RestaurantDocumentation extends Documentation {

    @Test
    void 선릉캠퍼스_식당_목록의_0페이지를_최신순으로_조회한다() {
        when(restaurantService.findByCampusIdAndCategoryId(eq(null), eq(ORDER_BY_ID_DESC.getQuery()), eq(2L), eq(null),
                any(Pageable.class))).thenReturn(SEOLLEUNG_RESTAURANTS_RESPONSE);

        docsGiven
                .when().get("/api/campuses/2/restaurants?page=0&size=10")
                .then().log().all()
                .apply(document("restaurants/list"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 선릉캠퍼스_식당_목록의_0페이지를_가나다순으로_조회한다() {
        when(restaurantService.findByCampusIdAndCategoryId(eq(null), eq(ORDER_BY_NAME_ASC.getQuery()), eq(2L), eq(null),
                any(Pageable.class))).thenReturn(SEOLLEUNG_RESTAURANTS_SORT_BY_SPELL_RESPONSE);

        docsGiven
                .when().get("/api/campuses/2/restaurants?filter=spell&page=0&size=10")
                .then().log().all()
                .apply(document("restaurants/list-spell"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 선릉캠퍼스_식당_목록의_0페이지를_별점순으로_조회한다() {
        when(restaurantService.findByCampusIdAndCategoryId(eq(null), eq(ORDER_BY_RATING_DESC.getQuery()), eq(2L),
                eq(null),
                any(Pageable.class))).thenReturn(SEOLLEUNG_RESTAURANTS_SORT_BY_RATING_RESPONSE);

        docsGiven
                .when().get("/api/campuses/2/restaurants?filter=rating&page=0&size=10")
                .then().log().all()
                .apply(document("restaurants/list-rating"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 선릉캠퍼스_한식_식당_목록의_0페이지를_최신순으로_조회한다() {
        when(restaurantService.findByCampusIdAndCategoryId(eq(null), eq(ORDER_BY_ID_DESC.getQuery()), eq(2L), eq(1L),
                any(Pageable.class))).thenReturn(SEOLLEUNG_KOREAN_RESTAURANTS_RESPONSE);

        docsGiven
                .when().get("/api/campuses/2/restaurants?categoryId=1&page=0&size=10")
                .then().log().all()
                .apply(document("restaurants/list-category"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 선릉캠퍼스_식당_목록을_2개_무작위로_조회한다() {
        when(restaurantService.findRandomsByCampusId(null, 2L, 2)).thenReturn(
                SEOLLEUNG_RESTAURANTS_RANDOM_2_RESPONSE);

        docsGiven
                .when().get("/api/campuses/2/restaurants/random?size=2")
                .then().log().all()
                .apply(document("restaurants/list-random"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 선릉캠퍼스_식당_1의_상세_정보를_조회한다() {
        when(restaurantService.findById(null, 1L)).thenReturn(SEOLLEUNG_RESTAURANT_RESPONSE_1);

        docsGiven
                .when().get("/api/restaurants/1")
                .then().log().all()
                .apply(document("restaurants"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 선릉캠퍼스_식당을_이름으로_검색한다() {
        when(restaurantService.findTitlesByCampusIdAndNameContainingIgnoreCaseIdDescSort(eq(null), eq(1L), eq("돈까스"),
                any(Pageable.class)))
                .thenReturn(SEOLLEUNG_PORT_CUTLET_RESTAURANTS_RESPONSE);

        docsGiven
                .when().get("/api/campuses/1/restaurants/search?name=돈까스&page=0&size=2")
                .then().log().all()
                .apply(document("restaurants/search"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 북마크_된_식당_목록을_조회한다() {
        when(restaurantService.findBookmarkedRestaurants("1")).thenReturn(SEOLLEUNG_RESTAURANTS_RANDOM_2_RESPONSE);

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().get("/api/restaurants/bookmarks")
                .then().log().all()
                .apply(document("restaurants/list-bookmark"))
                .statusCode(HttpStatus.OK.value());
    }
}

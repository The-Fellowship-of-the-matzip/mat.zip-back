package com.woowacourse.document;

import static com.woowacourse.document.DocumentationFixture.RESTAURANT_REQUESTS_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.matzip.presentation.request.RestaurantRequestCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantRequestUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class RestaurantRequestDocumentation extends Documentation {

    @Test
    void 식당_추가_요청을_작성한다() {
        doNothing().when(restaurantRequestService).createRequest(anyString(), anyLong(), any());

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .body(new RestaurantRequestCreateRequest(1L, "식당"))
                .when().post("/api/campuses/1/restaurants/requests")
                .then().log().all()
                .apply(document("restaurantRequests/create"))
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 식당_추가_요청을_조회한다() {
        when(restaurantRequestService.findPage(any(), any(), any(Pageable.class)))
                .thenReturn(RESTAURANT_REQUESTS_RESPONSE);

        docsGiven
                .header("Authorization", "Bearer jwt.token.here(Nullable)")
                .when().get("/api/campuses/1/restaurants/requests?page=0&size=5")
                .then().log().all()
                .apply(document("restaurantRequests/list"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 식당_추가_요청을_수정한다() {
        doNothing().when(restaurantRequestService)
                .updateRequest(anyString(), anyLong(), any(RestaurantRequestUpdateRequest.class));

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .body(new RestaurantRequestUpdateRequest(1L, "식당"))
                .when().put("/api/campuses/1/restaurants/requests/1")
                .then().log().all()
                .apply(document("restaurantRequests/update"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 식당_추가_요청을_삭제한다() {
        doNothing().when(restaurantRequestService)
                .deleteRequest(anyString(), anyLong());

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .when().delete("/api/campuses/1/restaurants/requests/1")
                .then().log().all()
                .apply(document("restaurantRequests/delete"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}

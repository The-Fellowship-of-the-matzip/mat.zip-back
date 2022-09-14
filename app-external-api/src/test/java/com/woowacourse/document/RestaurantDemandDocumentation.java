package com.woowacourse.document;

import static com.woowacourse.document.DocumentationFixture.RESTAURANT_REQUESTS_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.matzip.presentation.request.RestaurantDemandCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantDemandUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class RestaurantDemandDocumentation extends Documentation {

    @Test
    void 식당_추가_요청을_작성한다() {
        doNothing().when(restaurantDemandService).createRequest(anyString(), anyLong(), any());

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .body(new RestaurantDemandCreateRequest(1L, "식당"))
                .when().post("/api/campuses/1/restaurantDemands")
                .then().log().all()
                .apply(document("restaurantDemands/create"))
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 식당_추가_요청을_조회한다() {
        when(restaurantDemandService.findPage(any(), any(), any(Pageable.class)))
                .thenReturn(RESTAURANT_REQUESTS_RESPONSE);

        docsGiven
                .header("Authorization", "Bearer jwt.token.here(Nullable)")
                .when().get("/api/campuses/1/restaurantDemands?page=0&size=5")
                .then().log().all()
                .apply(document("restaurantDemands/list"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 식당_추가_요청을_수정한다() {
        doNothing().when(restaurantDemandService)
                .updateRequest(anyString(), anyLong(), any(RestaurantDemandUpdateRequest.class));

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .body(new RestaurantDemandUpdateRequest(1L, "식당"))
                .when().put("/api/campuses/1/restaurantDemands/1")
                .then().log().all()
                .apply(document("restaurantDemands/update"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 식당_추가_요청을_삭제한다() {
        doNothing().when(restaurantDemandService)
                .deleteRequest(anyString(), anyLong());

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .when().delete("/api/campuses/1/restaurantDemands/1")
                .then().log().all()
                .apply(document("restaurantDemands/delete"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}

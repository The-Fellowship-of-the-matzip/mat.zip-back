package com.woowacourse.document;

import static com.woowacourse.document.DocumentationFixture.RESTAURANT_RESPONSES;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RestaurantDocumentation extends Documentation {

    @Test
    void 선릉캠퍼스_식당_목록을_모두_조회한다() {
        when(restaurantService.findByCampusId(anyLong())).thenReturn(RESTAURANT_RESPONSES);

        docsGiven
                .when().get("/api/campuses/2/restaurants")
                .then().log().all()
                .apply(document("restaurants/list"))
                .statusCode(HttpStatus.OK.value());
    }
}

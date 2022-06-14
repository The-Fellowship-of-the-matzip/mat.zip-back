package com.woowacourse.document;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReviewDocumentation extends Documentation {

    @Test
    void 리뷰를_작성한다() {
        doNothing().when(reviewService).createReview(anyString(), anyLong(), any());

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .body(new ReviewCreateRequest("맛있네요.", 4, "무닭볶음탕 (중)"))
                .when().post("/api/restaurants/1/reviews")
                .then().log().all()
                .apply(document("reviewes/create"))
                .statusCode(HttpStatus.CREATED.value());
    }
}

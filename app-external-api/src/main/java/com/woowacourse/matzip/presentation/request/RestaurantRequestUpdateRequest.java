package com.woowacourse.matzip.presentation.request;

import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class RestaurantRequestUpdateRequest {

    @Positive(message = "카테고리 id는 1 이상이어야 합니다.")
    private Long categoryId;
    @NotNull(message = "식당 이름은 null이 들어올 수 없습니다.")
    private String name;

    private RestaurantRequestUpdateRequest() {
    }

    public RestaurantRequestUpdateRequest(final long categoryId, final String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public RestaurantRequest toRestaurantRequest() {
        return RestaurantRequest.builder()
                .categoryId(categoryId)
                .name(name)
                .build();
    }
}

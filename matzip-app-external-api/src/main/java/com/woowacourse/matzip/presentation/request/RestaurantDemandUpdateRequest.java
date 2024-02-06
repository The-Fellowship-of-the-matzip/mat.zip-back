package com.woowacourse.matzip.presentation.request;

import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class RestaurantDemandUpdateRequest {

    @Positive(message = "카테고리 id는 1 이상이어야 합니다.")
    private Long categoryId;
    @NotNull(message = "식당 이름은 null이 들어올 수 없습니다.")
    private String name;

    private RestaurantDemandUpdateRequest() {
    }

    public RestaurantDemandUpdateRequest(final long categoryId, final String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public RestaurantDemand toRestaurantRequest() {
        return RestaurantDemand.builder()
                .categoryId(categoryId)
                .name(name)
                .build();
    }
}

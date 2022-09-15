package com.woowacourse.matzip.presentation.request;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class RestaurantDemandCreateRequest {

    @Positive(message = "카테고리 id는 1 이상이어야 합니다.")
    private Long categoryId;
    @NotNull(message = "식당 이름은 null이 들어올 수 없습니다.")
    private String name;

    private RestaurantDemandCreateRequest() {
    }

    public RestaurantDemandCreateRequest(final long categoryId, final String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public RestaurantDemand toRestaurantRequestWithMemberAndCampusId(final Member member, final Long campusId) {
        return RestaurantDemand.builder()
                .categoryId(categoryId)
                .campusId(campusId)
                .name(name)
                .member(member)
                .build();
    }
}

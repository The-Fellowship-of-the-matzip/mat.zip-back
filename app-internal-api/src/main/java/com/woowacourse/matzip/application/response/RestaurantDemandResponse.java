package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.category.Category;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import lombok.Getter;

@Getter
public class RestaurantDemandResponse {

    private Long id;
    private String categoryName;
    private String campusName;
    private String name;
    private Member member;
    private boolean registered;

    private RestaurantDemandResponse() {
    }

    private RestaurantDemandResponse(final Long id, final String categoryName, final String campusName,
                                     final String name, final Member member, final boolean registered) {
        this.id = id;
        this.categoryName = categoryName;
        this.campusName = campusName;
        this.name = name;
        this.member = member;
        this.registered = registered;
    }

    public static RestaurantDemandResponse of(final RestaurantRequest restaurantRequest, final Category category,
                                              final Campus campus) {
        return new RestaurantDemandResponse(
                restaurantRequest.getId(),
                category.getName(),
                campus.getName(),
                restaurantRequest.getName(),
                restaurantRequest.getMember(),
                restaurantRequest.isRegistered()
        );
    }
}

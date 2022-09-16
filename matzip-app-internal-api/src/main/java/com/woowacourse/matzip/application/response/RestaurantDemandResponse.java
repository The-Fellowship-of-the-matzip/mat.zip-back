package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.category.Category;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RestaurantDemandResponse {

    private Long id;
    private String categoryName;
    private String campusName;
    private String name;
    private Member member;
    private boolean registered;
    private LocalDateTime createdAt;

    private RestaurantDemandResponse() {
    }

    private RestaurantDemandResponse(final Long id, final String categoryName, final String campusName,
                                     final String name, final Member member, final boolean registered, final LocalDateTime createdAt) {
        this.id = id;
        this.categoryName = categoryName;
        this.campusName = campusName;
        this.name = name;
        this.member = member;
        this.registered = registered;
        this.createdAt = createdAt;
    }

    public static RestaurantDemandResponse of(final RestaurantDemand restaurantDemand, final Category category,
                                              final Campus campus) {
        return new RestaurantDemandResponse(
                restaurantDemand.getId(),
                category.getName(),
                campus.getName(),
                restaurantDemand.getName(),
                restaurantDemand.getMember(),
                restaurantDemand.isRegistered(),
                restaurantDemand.getCreatedAt()
        );
    }
}

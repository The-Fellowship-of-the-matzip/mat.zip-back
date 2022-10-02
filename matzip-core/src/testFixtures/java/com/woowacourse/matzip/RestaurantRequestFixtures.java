package com.woowacourse.matzip;

public enum RestaurantRequestFixtures {

    RESTAURANT_REQUEST_1(1L, 1L, "식당", false);

    private final Long categoryId;
    private final Long campusId;
    private final String name;
    private final boolean registered;

    RestaurantRequestFixtures(final Long categoryId, final Long campusId, final String name, final boolean registered) {
        this.categoryId = categoryId;
        this.campusId = campusId;
        this.name = name;
        this.registered = registered;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getCampusId() {
        return campusId;
    }

    public String getName() {
        return name;
    }

    public boolean isRegistered() {
        return registered;
    }
}

package com.woowacourse.matzip.exception;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException() {
        super("존재하지 않는 식당입니다.");
    }
}

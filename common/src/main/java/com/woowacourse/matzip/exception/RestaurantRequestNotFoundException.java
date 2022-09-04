package com.woowacourse.matzip.exception;

public class RestaurantRequestNotFoundException extends RuntimeException {

    public RestaurantRequestNotFoundException() {
        super("존재하지 않는 식당 추가 요청입니다.");
    }
}

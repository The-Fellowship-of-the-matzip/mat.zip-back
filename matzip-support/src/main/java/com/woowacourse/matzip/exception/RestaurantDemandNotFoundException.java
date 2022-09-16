package com.woowacourse.matzip.exception;

public class RestaurantDemandNotFoundException extends RuntimeException {

    public RestaurantDemandNotFoundException() {
        super("존재하지 않는 식당 추가 요청입니다.");
    }
}

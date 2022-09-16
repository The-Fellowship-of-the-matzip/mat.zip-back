package com.woowacourse.matzip.exception;

public class AlreadyRegisteredException extends RuntimeException {

    public AlreadyRegisteredException() {
        super("이미 등록 완료된 음식점 요청입니다.");
    }
}

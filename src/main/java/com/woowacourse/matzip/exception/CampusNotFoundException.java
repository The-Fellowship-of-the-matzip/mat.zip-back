package com.woowacourse.matzip.exception;

public class CampusNotFoundException extends RuntimeException {

    public CampusNotFoundException() {
        super("존재하지 않는 캠퍼스입니다.");
    }
}

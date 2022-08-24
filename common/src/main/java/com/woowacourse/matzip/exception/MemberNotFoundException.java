package com.woowacourse.matzip.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("존재하지 않는 유저입니다.");
    }
}

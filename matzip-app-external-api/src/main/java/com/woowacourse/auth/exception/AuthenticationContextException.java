package com.woowacourse.auth.exception;

public class AuthenticationContextException extends RuntimeException {

    public AuthenticationContextException() {
        super("Authority 권한을 사용할 수 없습니다.");
    }
}

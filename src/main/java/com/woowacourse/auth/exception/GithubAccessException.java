package com.woowacourse.auth.exception;

public class GithubAccessException extends RuntimeException {

    public GithubAccessException() {
        super("[ERROR] Github에 접근할 수 없습니다.");
    }
}

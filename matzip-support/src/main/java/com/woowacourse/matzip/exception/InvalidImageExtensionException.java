package com.woowacourse.matzip.exception;

public class InvalidImageExtensionException extends RuntimeException {
    public InvalidImageExtensionException() {
        super("유효하지 않은 이미지 확장자명입니다.");
    }
}

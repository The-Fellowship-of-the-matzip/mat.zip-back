package com.woowacourse.matzip.domain.image;

public class InValidImageExtensionException extends RuntimeException {

    public InValidImageExtensionException() {
        super("유효하지 않은 확장자입니다.");
    }
}

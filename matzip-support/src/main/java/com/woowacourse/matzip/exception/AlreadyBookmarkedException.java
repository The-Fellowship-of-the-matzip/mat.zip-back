package com.woowacourse.matzip.exception;

public class AlreadyBookmarkedException extends RuntimeException {

    public AlreadyBookmarkedException() {
        super("이미 등록된 북마크 요청입니다.");
    }
}

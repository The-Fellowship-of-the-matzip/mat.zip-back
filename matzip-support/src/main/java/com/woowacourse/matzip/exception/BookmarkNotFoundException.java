package com.woowacourse.matzip.exception;

public class BookmarkNotFoundException extends RuntimeException {

    public BookmarkNotFoundException() {
        super("존재하지 않는 북마크입니다.");
    }
}

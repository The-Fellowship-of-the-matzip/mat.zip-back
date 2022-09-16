package com.woowacourse.matzip.exception;

public class InvalidSortConditionException extends RuntimeException {

    public InvalidSortConditionException() {
        super("올바르지 않은 정렬 기준입니다.");
    }
}

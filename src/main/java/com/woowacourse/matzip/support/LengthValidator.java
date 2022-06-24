package com.woowacourse.matzip.support;

import com.woowacourse.matzip.exception.InvalidLengthException;

public class LengthValidator {

    private LengthValidator() {
        throw new AssertionError();
    }

    public static void checkStringLength(final String value, final String name, final int max) {
        if (value.length() > max) {
            throw new InvalidLengthException(String.format("%s는 %d보다 작은 값만 입력할 수 있습니다.", name, max));
        }
    }
}

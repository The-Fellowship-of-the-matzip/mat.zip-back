package com.woowacourse.matzip.support;

import com.woowacourse.matzip.exception.InvalidLengthException;

public class LengthValidator {

    private LengthValidator() {
        throw new AssertionError();
    }

    public static void checkStringLength(final String value, final int max, final String name) {
        if (value.length() > max) {
            throw new InvalidLengthException(String.format("%s은(는) %d보다 작은 값만 입력할 수 있습니다.", name, max));
        }
    }

    public static void checkStringLength(final String value, final int max, final int min, final String name) {
        if (value.length() < min || value.length() > max) {
            throw new InvalidLengthException(String.format("%s은(는) %d와 %d 사이 값만 입력할 수 있습니다.", name, min, max));
        }
    }
}

package com.woowacourse.support;

import java.util.Arrays;

public enum Tables {

    MEMBER("member"),
    REVIEW("review");

    private final String name;

    Tables(final String name) {
        this.name = name;
    }

    public static boolean hasName(final String name) {
        return Arrays.stream(values())
                .anyMatch(value -> value.name.equalsIgnoreCase(name));
    }
}

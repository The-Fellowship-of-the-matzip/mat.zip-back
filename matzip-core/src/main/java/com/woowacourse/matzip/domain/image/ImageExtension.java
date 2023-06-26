package com.woowacourse.matzip.domain.image;

import java.util.Arrays;

public enum ImageExtension {
    JPG,
    JPEG,
    PNG,
    WEBP,
    GIF;

    public static String validateExtension(final String extension) {
        return Arrays.stream(values())
                .map(imageExtension -> imageExtension.name().toLowerCase())
                .filter(imageExtension -> imageExtension.equalsIgnoreCase(extension))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("업로드 할 수 없는 확장자명입니다.")
                );
    }
}

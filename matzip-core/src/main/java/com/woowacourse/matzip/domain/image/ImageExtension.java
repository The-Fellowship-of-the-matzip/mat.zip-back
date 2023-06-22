package com.woowacourse.matzip.domain.image;

import java.util.Arrays;

public enum ImageExtension {
    JPG,
    JPEG,
    PNG,
    WEBP,
    GIF;

    public static void validateExtension(final String extension) {
        Arrays.stream(values())
                .filter(imageExtension -> imageExtension.name().equalsIgnoreCase(extension))
                .findFirst()
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("업로드 할 수 없는 확장자명입니다.");
                });
    }
}

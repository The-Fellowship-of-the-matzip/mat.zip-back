package com.woowacourse.matzip.domain.image;

import com.woowacourse.matzip.exception.InvalidImageExtensionException;

import java.util.Arrays;
import java.util.Objects;

public enum ImageExtension {
    JPG,
    JPEG,
    PNG,
    WEBP,
    GIF;

    public static void validateExtension(String fileName) {
        final String extension = Objects.requireNonNull(fileName)
                .substring(fileName.lastIndexOf('.') + 1);

        Arrays.stream(values())
                .filter(imageExtension -> imageExtension.name().equalsIgnoreCase(extension))
                .findAny()
                .orElseThrow(InvalidImageExtensionException::new);
    }
}

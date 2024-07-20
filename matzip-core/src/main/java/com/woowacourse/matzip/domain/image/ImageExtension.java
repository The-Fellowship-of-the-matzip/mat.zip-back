package com.woowacourse.matzip.domain.image;

import java.util.Arrays;
import java.util.Objects;

public enum ImageExtension {
    JPG,
    JPEG,
    PNG,
    WEBP,
    GIF;

    private static final String DELIMITER = ".";

    public static void validateExtension(final String fileName) {
        Objects.requireNonNull(fileName);
        String extension = fileName.substring(fileName.lastIndexOf(DELIMITER) + 1);
        boolean isNotValidExtension = Arrays.stream(ImageExtension.values())
                .noneMatch(imageExtension -> imageExtension.name().equalsIgnoreCase(extension));

        if (isNotValidExtension) {
            throw new InValidImageExtensionException();
        }
    }
}

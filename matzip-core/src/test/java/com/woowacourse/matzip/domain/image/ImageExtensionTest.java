package com.woowacourse.matzip.domain.image;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageExtensionTest {

    @Test
    void 확장자_검증() {
        String extension = "png";

        String result = ImageExtension.validateExtension(extension);

        assertThat(result).isEqualTo("png");
    }
}

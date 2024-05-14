package com.woowacourse.matzip.domain.image;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.woowacourse.matzip.domain.image.ImageExtension.validateExtension;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageExtensionTest {

    @ParameterizedTest
    @ValueSource(strings = {"aa.png", "bb.jpg", "cc.jpeg", "dd.webp", "ee.gif", "aa.PNG", "bb.JPG"})
    void 유효한_확장자일_때_예외가_발생하지_않는다(final String fileName) {
        assertThatCode(() -> validateExtension(fileName))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa.avi", "kk.zip"})
    void 유효하지_않은_확장자일_때_예외가_발생한다(final String fileName) {
        assertThatThrownBy(() -> validateExtension(fileName))
                .isInstanceOf(InValidImageExtensionException.class);
    }
}

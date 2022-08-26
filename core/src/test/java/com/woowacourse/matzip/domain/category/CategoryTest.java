package com.woowacourse.matzip.domain.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.exception.InvalidLengthException;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void 카테고리의_이름_제한길이_초과시_예외발생() {
        assertThatThrownBy(() -> new Category(1L, "abcdefghijk"))
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage("카테고리의 이름은(는) 길이가 10 이하의 값만 입력할 수 있습니다.");
    }

    @Test
    void 카테고리_생성() {
        assertThat(new Category(1L, "한식")).isInstanceOf(Category.class);
    }

    @Test
    void equalsHashCode() {
        assertThat(new Category(1L, "한식"))
                .isEqualTo(new Category(1L, "한식"))
                .hasSameHashCodeAs(new Category(1L, "한식"));
    }
}

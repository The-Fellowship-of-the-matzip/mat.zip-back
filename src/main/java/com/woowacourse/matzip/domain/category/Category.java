package com.woowacourse.matzip.domain.category;

import com.woowacourse.matzip.exception.InvalidCategoryException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "category")
@Getter
public class Category {

    private static final int CATEGORY_NAME_LIMIT_LENGTH = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    protected Category() {
    }

    public Category(final Long id, final String name) {
        checkCategoryLength(name);
        this.id = id;
        this.name = name;
    }

    private void checkCategoryLength(final String name) {
        if (name.length() > CATEGORY_NAME_LIMIT_LENGTH) {
            throw new InvalidCategoryException(String.format("카테고리의 이름은 %d자를 넘을 수 없습니다.", CATEGORY_NAME_LIMIT_LENGTH));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

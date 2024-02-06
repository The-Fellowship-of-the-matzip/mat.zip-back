package com.woowacourse.matzip.domain.category;

import com.woowacourse.matzip.support.LengthValidator;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "category")
@Getter
public class Category {

    private static final int CATEGORY_NAME_LIMIT_LENGTH = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = CATEGORY_NAME_LIMIT_LENGTH, nullable = false, unique = true)
    private String name;

    protected Category() {
    }

    @Builder
    public Category(final Long id, final String name) {
        LengthValidator.checkStringLength(name, CATEGORY_NAME_LIMIT_LENGTH, "카테고리의 이름");
        this.id = id;
        this.name = name;
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

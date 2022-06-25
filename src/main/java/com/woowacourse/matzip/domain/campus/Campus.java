package com.woowacourse.matzip.domain.campus;

import com.woowacourse.matzip.support.LengthValidator;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "campus")
@Getter
public class Campus {

    private static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = MAX_NAME_LENGTH, nullable = false, unique = true)
    private String name;

    protected Campus() {
    }

    @Builder
    public Campus(final Long id, final String name) {
        LengthValidator.checkStringLength(name, MAX_NAME_LENGTH, "캠퍼스 이름");
        this.id = id;
        this.name = name;
    }

    public boolean isSameId(final Long id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Campus campus = (Campus) o;
        return Objects.equals(id, campus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

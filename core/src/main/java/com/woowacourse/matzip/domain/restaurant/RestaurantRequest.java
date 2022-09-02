package com.woowacourse.matzip.domain.restaurant;

import com.woowacourse.matzip.exception.AlreadyRegisteredException;
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
@Table(name = "restaurant_request")
@Getter
public class RestaurantRequest {

    private static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "campus_id", nullable = false)
    private Long campusId;

    @Column(name = "name", length = MAX_NAME_LENGTH, nullable = false)
    private String name;

    @Column(name = "registered", nullable = false)
    private boolean registered;

    protected RestaurantRequest() {
    }

    @Builder
    public RestaurantRequest(final Long id, final Long categoryId, final Long campusId, final String name,
                             final boolean registered) {
        LengthValidator.checkStringLength(name, MAX_NAME_LENGTH, "식당 이름");
        this.id = id;
        this.categoryId = categoryId;
        this.campusId = campusId;
        this.name = name;
        this.registered = registered;
    }

    public void update(final RestaurantRequest updateRequest) {
        this.categoryId = updateRequest.categoryId;
        this.campusId = updateRequest.campusId;
        this.name = updateRequest.name;
    }

    public void register() {
        validateNotRegistered();
        registered = true;
    }

    private void validateNotRegistered() {
        if (registered) {
            throw new AlreadyRegisteredException();
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
        final RestaurantRequest that = (RestaurantRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

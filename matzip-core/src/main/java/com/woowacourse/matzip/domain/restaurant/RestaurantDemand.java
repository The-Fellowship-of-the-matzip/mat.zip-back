package com.woowacourse.matzip.domain.restaurant;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.exception.AlreadyRegisteredException;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.support.LengthValidator;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "restaurant_request")
@EntityListeners(AuditingEntityListener.class)
@Getter
public class RestaurantDemand {

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

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "registered", nullable = false)
    private boolean registered;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    protected RestaurantDemand() {
    }

    @Builder
    public RestaurantDemand(final Long id, final Long categoryId, final Long campusId, final String name,
                            final Member member, final boolean registered) {
        LengthValidator.checkStringLength(name, MAX_NAME_LENGTH, "식당 이름");
        this.id = id;
        this.categoryId = categoryId;
        this.campusId = campusId;
        this.name = name;
        this.member = member;
        this.registered = registered;
    }

    public void update(final RestaurantDemand updateRequest, final String githubId) {
        validateWriter(githubId);
        validateNotRegistered();
        this.categoryId = updateRequest.categoryId;
        this.name = updateRequest.name;
    }

    public void validateWriter(final String githubId) {
        if (!isWriter(githubId)) {
            throw new ForbiddenException("식당 추가 요청에 대한 권한이 없습니다.");
        }
    }

    public boolean isWriter(final String githubId) {
        return member.isSameGithubId(githubId);
    }

    public void register() {
        validateNotRegistered();
        registered = true;
    }

    public void validateNotRegistered() {
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
        final RestaurantDemand that = (RestaurantDemand) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

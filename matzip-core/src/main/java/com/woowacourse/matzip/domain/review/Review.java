package com.woowacourse.matzip.domain.review;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.exception.InvalidReviewException;
import com.woowacourse.matzip.support.LengthValidator;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "review")
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Review extends AbstractAggregateRoot<Review> {

    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 5;
    private static final int MAX_MENU_LENGTH = 20;
    private static final int MAX_CONTENT_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "content", length = MAX_CONTENT_LENGTH)
    private String content;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "menu", length = MAX_MENU_LENGTH, nullable = false)
    private String menu;

    @Column(name = "image_url")
    private String imageUrl;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    protected Review() {
    }

    @Builder
    public Review(final Long id, final Member member, final Long restaurantId, final String content, final int rating,
                  final String menu, final String imageUrl, final LocalDateTime createdAt) {
        validateRating(rating);
        LengthValidator.checkStringLength(menu, MAX_MENU_LENGTH, "메뉴의 이름");
        LengthValidator.checkStringLength(content, MAX_CONTENT_LENGTH, "리뷰 내용");
        this.id = id;
        this.member = member;
        this.restaurantId = restaurantId;
        this.content = content;
        this.rating = rating;
        this.menu = menu;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        registerEvent(new ReviewCreatedEvent(restaurantId, rating));
    }

    private void validateRating(final int rating) {
        if (rating < MIN_SCORE || rating > MAX_SCORE) {
            throw new InvalidReviewException(String.format("리뷰 점수는 %d점부터 %d점까지만 가능합니다.", MIN_SCORE, MAX_SCORE));
        }
    }

    public void update(final String githubId,
                       final String content,
                       final int rating,
                       final String menu) {
        validateOwner(githubId);
        validateRating(rating);
        LengthValidator.checkStringLength(menu, MAX_MENU_LENGTH, "메뉴의 이름");
        LengthValidator.checkStringLength(content, MAX_CONTENT_LENGTH, "리뷰 내용");
        registerEvent(new ReviewUpdatedEvent(restaurantId, calculateGap(rating)));
        this.content = content;
        this.rating = rating;
        this.menu = menu;
    }

    private void validateOwner(final String githubId) {
        if (!isWriter(githubId)) {
            throw new ForbiddenException("리뷰를 업데이트 할 권한이 없습니다.");
        }
    }

    public boolean isWriter(final @Nullable String githubId) {
        return member.isSameGithubId(githubId);
    }

    private int calculateGap(final int rating) {
        validateRating(rating);
        return Math.subtractExact(rating, this.rating);
    }

    @PreRemove
    private void registerDeletedEvent() {
        registerEvent(new ReviewDeletedEvent(restaurantId, rating));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

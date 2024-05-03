package com.woowacourse.matzip.domain.review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "review_event",
        indexes = @Index(
                name = "IDX_REVIEW_EVENT_DATE_STATUS",
                columnList = "execution_date, event_status")
)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class ReviewEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "rating_gap", nullable = false)
    private int ratingGap;

    @Column(name = "review_count", nullable = false)
    private int reviewCount;

    @Column(name = "execution_date", nullable = false)
    private LocalDate executionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", nullable = false)
    private EventStatus eventStatus;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    protected ReviewEvent() {

    }

    public ReviewEvent(final Long id,
                       final Long reviewId,
                       final Long restaurantId,
                       final int ratingGap,
                       final int reviewCount,
                       final LocalDate executionDate,
                       final EventStatus eventStatus) {
        this.id = id;
        this.reviewId = reviewId;
        this.restaurantId = restaurantId;
        this.ratingGap = ratingGap;
        this.reviewCount = reviewCount;
        this.executionDate = executionDate;
        this.eventStatus = eventStatus;
    }

    public ReviewEvent(final Long reviewId,
                       final Long restaurantId,
                       final int ratingGap,
                       final int reviewCount,
                       final LocalDate executionDate,
                       final EventStatus eventStatus) {
        this(null, reviewId, restaurantId, ratingGap, reviewCount, executionDate, eventStatus);
    }

    public void complete() {
        this.eventStatus = EventStatus.COMPLETE;
    }

    @Override
    public String toString() {
        return "ReviewEvent{" +
                "id=" + id +
                ", reviewId=" + reviewId +
                ", restaurantId=" + restaurantId +
                ", ratingGap=" + ratingGap +
                ", reviewCount=" + reviewCount +
                ", executionDate=" + executionDate +
                ", eventStatus=" + eventStatus +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}

package com.woowacourse.matzip.domain.review;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.exception.InvalidReviewException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "review")
@Getter
public class Review {

    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "content")
    private String content;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "menu", length = 20, nullable = false)
    private String menu;

    protected Review() {
    }

    @Builder
    public Review(final Long id, final Member member, final Long restaurantId, final String content, final int score,
                  final String menu) {
        validateScoreLength(score);
        this.id = id;
        this.member = member;
        this.restaurantId = restaurantId;
        this.content = content;
        this.score = score;
        this.menu = menu;
    }

    private void validateScoreLength(final int score) {
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw new InvalidReviewException("리뷰 점수는 0점부터 5점까지만 가능합니다.");
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
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

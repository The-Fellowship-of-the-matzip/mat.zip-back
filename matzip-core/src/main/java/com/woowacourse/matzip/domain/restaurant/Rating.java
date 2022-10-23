package com.woowacourse.matzip.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

@Embeddable
@Getter
public class Rating {

    @Column(name = "review_count", nullable = false)
    private int count;

    @Column(name = "review_sum", nullable = false)
    private long sum;

    @Column(name = "review_avg", nullable = false)
    private float average;

    protected Rating() {
    }

    @Builder
    public Rating(final int count, final long sum) {
        this.count = count;
        this.sum = sum;
        this.average = calculateAverage(count, sum);
    }

    private float calculateAverage(final int count, final long sum) {
        return (float) (sum * 100 / count) / 100;
    }
}

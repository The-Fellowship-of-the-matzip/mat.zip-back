package com.woowacourse.matzip.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

@Embeddable
@Getter
public class Rating {

    @Column(name = "review_count")
    private int count = 0;

    @Column(name = "review_sum")
    private long sum = 0;

    @Column(name = "review_avg")
    private float average = 0;

    protected Rating() {
    }

    @Builder
    public Rating(final int count, final long sum) {
        this.count = count;
        this.sum = sum;
        this.average = calculateAverage(count, sum);
    }

    private float calculateAverage(final int count, final long sum) {
        if (count == 0) {
            return 0;
        }
        return (float) (sum * 100 / count) / 100;
    }
}

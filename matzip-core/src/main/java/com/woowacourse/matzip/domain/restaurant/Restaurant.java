package com.woowacourse.matzip.domain.restaurant;

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
@Table(name = "restaurant")
@Getter
public class Restaurant {

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

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "distance", nullable = false)
    private long distance;

    @Column(name = "kakao_map_url", nullable = false)
    private String kakaoMapUrl;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "review_count")
    private int reviewCount;

    @Column(name = "review_sum")
    private long reviewRatingSum;

    @Column(name = "review_avg")
    private float reviewRatingAverage;

    protected Restaurant() {
    }

    @Builder
    public Restaurant(final Long id, final Long categoryId, final Long campusId, final String name,
                      final String address, final long distance, final String kakaoMapUrl, final String imageUrl,
                      final int reviewCount, final long reviewRatingSum, final float reviewRatingAverage) {
        LengthValidator.checkStringLength(name, MAX_NAME_LENGTH, "식당 이름");
        this.id = id;
        this.categoryId = categoryId;
        this.campusId = campusId;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.kakaoMapUrl = kakaoMapUrl;
        this.imageUrl = imageUrl;
        this.reviewCount = reviewCount;
        this.reviewRatingSum = reviewRatingSum;
        this.reviewRatingAverage = reviewRatingAverage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

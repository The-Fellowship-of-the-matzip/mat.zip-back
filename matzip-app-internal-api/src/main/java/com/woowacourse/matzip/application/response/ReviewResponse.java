package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.review.Review;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponse {

    private final Long id;
    private final String writerName;
    private final String restaurantName;
    private final String content;
    private final int rating;
    private final String menu;
    private final LocalDateTime createdAt;

    private ReviewResponse(final Long id, final String writerName, final String restaurantName, final String content,
                          final int rating, final String menu, final LocalDateTime createdAt) {
        this.id = id;
        this.writerName = writerName;
        this.restaurantName = restaurantName;
        this.content = content;
        this.rating = rating;
        this.menu = menu;
        this.createdAt = createdAt;
    }

    public static ReviewResponse of(final Review review, final Restaurant restaurant) {
        return new ReviewResponse(
                review.getId(),
                review.getMember().getUsername(),
                restaurant.getName(),
                review.getContent(),
                review.getRating(),
                review.getMenu(),
                review.getCreatedAt()
        );
    }
}

package com.woowacourse.matzip.presentation;

import com.woowacourse.auth.support.AuthenticationPrincipal;
import com.woowacourse.matzip.application.ReviewService;
import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(final ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Void> createReview(@PathVariable Long restaurantId,
                                             @RequestBody ReviewCreateRequest reviewCreateRequest,
                                             @AuthenticationPrincipal String githubId) {
        reviewService.createReview(githubId, restaurantId, reviewCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

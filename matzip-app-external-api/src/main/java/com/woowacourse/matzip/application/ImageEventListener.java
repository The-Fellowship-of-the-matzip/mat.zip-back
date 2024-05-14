package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.review.ReviewCreatedEvent;
import com.woowacourse.matzip.domain.review.ReviewDeletedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Async(value = "asyncTaskExecutor")
public class ImageEventListener {

    private final ImageService imageService;

    public ImageEventListener(final ImageService imageService) {
        this.imageService = imageService;
    }

    @TransactionalEventListener
    public void handleReviewCreatedEvent(final ReviewCreatedEvent event) {
        imageService.deleteUsingImage(event.getImageUrl());
    }

    @TransactionalEventListener
    public void handleReviewDeletedEvent(final ReviewDeletedEvent event) {
        imageService.deleteImageWhenReviewDeleted(event.getImageUrl());
    }
}

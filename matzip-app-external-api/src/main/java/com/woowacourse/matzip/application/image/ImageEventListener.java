package com.woowacourse.matzip.application.image;

import com.woowacourse.matzip.domain.image.UnusedImage;
import com.woowacourse.matzip.domain.review.ReviewCreatedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Async(value = "asyncTaskExecutor")
public class ImageEventListener {

    private final ImageDeleter imageDeleter;

    public ImageEventListener(ImageDeleter imageDeleter) {
        this.imageDeleter = imageDeleter;
    }

    @TransactionalEventListener
    public void handleReviewCreatedEvent(final ReviewCreatedEvent event) {
        imageDeleter.addUnusedImage(UnusedImage.builder()
                .imageUrl(event.getImageUrl())
                .build());
    }
}

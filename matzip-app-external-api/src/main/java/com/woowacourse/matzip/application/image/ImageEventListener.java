package com.woowacourse.matzip.application.image;

import com.woowacourse.matzip.domain.image.UnusedImage;
import com.woowacourse.matzip.domain.image.UnusedImageRepository;
import com.woowacourse.matzip.domain.review.ReviewCreatedEvent;
import com.woowacourse.matzip.domain.review.ReviewDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async(value = "asyncTaskExecutor")
public class ImageEventListener {

    private final UnusedImageRepository unusedImageRepository;

    public ImageEventListener(final UnusedImageRepository unusedImageRepository) {
        this.unusedImageRepository = unusedImageRepository;
    }

    @EventListener
    public void handleReviewCreatedEvent(final ReviewCreatedEvent event) {
        unusedImageRepository.delete(UnusedImage.builder()
                .imageUrl(event.getImageUrl())
                .build());
    }

    @EventListener
    public void handleReviewDeletedEvent(final ReviewDeletedEvent event) {
        unusedImageRepository.save(UnusedImage.builder()
                .imageUrl(event.getImageUrl())
                .build());
    }
}

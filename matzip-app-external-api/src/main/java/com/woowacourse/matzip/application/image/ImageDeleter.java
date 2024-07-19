package com.woowacourse.matzip.application.image;

import com.woowacourse.matzip.domain.image.UnusedImage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@Component
public class ImageDeleter {

    private static final int DELETE_BATCH_SIZE = 50;

    private final ImageManager imageManager;
    private final PriorityQueue<UnusedImage> unusedImages = new PriorityQueue<>(
            Comparator.comparing(UnusedImage::getCreatedAt)
    );

    public ImageDeleter(final ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    public void deleteImages() {
        List<UnusedImage> images = new ArrayList<>();
        LocalDate dateBoundary = LocalDate.now().minusDays(1);

        while ((!unusedImages.isEmpty() && unusedImages.peek().beforeDate(dateBoundary))
                || images.size() <= DELETE_BATCH_SIZE) {
            images.add(unusedImages.poll());
        }

        try {
            imageManager.deleteImages(images);
        } catch (Exception e) {
            unusedImages.addAll(images);
        }
    }
}

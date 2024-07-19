package com.woowacourse.matzip.application.image;

import com.woowacourse.matzip.domain.image.UnusedImage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class ImageDeleter {

    private static final int DELETE_BATCH_SIZE = 50;

    private final ImageManager imageManager;
    private final Queue<UnusedImage> unusedImages = new LinkedList<>();

    public ImageDeleter(final ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    public void deleteImages() {
        List<UnusedImage> images = new ArrayList<>();

        while (!unusedImages.isEmpty() || images.size() <= DELETE_BATCH_SIZE) {
            images.add(unusedImages.poll());
        }

        imageManager.deleteImages(images);
    }
}

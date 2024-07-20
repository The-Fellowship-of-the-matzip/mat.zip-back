package com.woowacourse.matzip.application.image;

import com.woowacourse.matzip.domain.image.UnusedImage;
import com.woowacourse.matzip.domain.image.UnusedImageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ImageDeleter {

    private final ImageManager imageManager;
    private final UnusedImageRepository unusedImageRepository;

    public ImageDeleter(final ImageManager imageManager, final UnusedImageRepository unusedImageRepository) {
        this.imageManager = imageManager;
        this.unusedImageRepository = unusedImageRepository;
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void deleteImages() {
        LocalDate dateBoundary = LocalDate.now().minusDays(1);

        List<UnusedImage> images = unusedImageRepository.findAllByCreatedAtBefore(dateBoundary.atStartOfDay());

        imageManager.deleteImages(images);
        unusedImageRepository.deleteAllBy(images);
    }
}

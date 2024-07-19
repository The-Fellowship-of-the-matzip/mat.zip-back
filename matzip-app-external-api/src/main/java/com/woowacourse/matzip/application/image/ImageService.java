package com.woowacourse.matzip.application.image;

import com.woowacourse.matzip.application.response.ImageUploadResponse;
import com.woowacourse.matzip.domain.image.ImageExtension;
import com.woowacourse.matzip.domain.image.UnusedImage;
import com.woowacourse.matzip.domain.image.UnusedImageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ImageService {

    private final ImageManager imageManager;
    private final UnusedImageRepository unusedImageRepository;

    public ImageService(final ImageManager imageManager, final UnusedImageRepository unusedImageRepository) {
        this.imageManager = imageManager;
        this.unusedImageRepository = unusedImageRepository;
    }

    @Transactional
    public ImageUploadResponse uploadImage(final MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        ImageExtension.validateExtension(originalFileName);
        String imageUrl = imageManager.uploadImage(file);
        unusedImageRepository.save(
                UnusedImage.builder()
                        .imageUrl(imageUrl)
                        .build()
        );
        return new ImageUploadResponse(imageUrl);
    }

    @Transactional
    public void deleteUsingImage(final String imageUrl) {
        unusedImageRepository.deleteByImageUrl(imageUrl);
    }

    @Transactional
    public void deleteImageWhenReviewDeleted(final String imageUrl) {
        imageManager.deleteImage(imageUrl);
        unusedImageRepository.deleteByImageUrl(imageUrl);
    }

    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void deleteUnusedImages() {
        LocalDateTime deleteBoundary = LocalDate.now().atStartOfDay();
        List<UnusedImage> unusedImages = unusedImageRepository.findAllByCreatedAtBefore(deleteBoundary);
        unusedImageRepository.deleteAllByCreatedAtBefore(deleteBoundary);
        imageManager.deleteImages(unusedImages);
    }
}

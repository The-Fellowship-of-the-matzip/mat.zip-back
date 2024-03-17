package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.image.UnusedImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploader {

    String uploadImage(final MultipartFile file);

    void deleteImage(final String imageUrl);

    void deleteImages(final List<UnusedImage> unusedImages);
}

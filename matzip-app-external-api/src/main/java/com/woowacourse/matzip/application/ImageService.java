package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.ImageResponse;
import com.woowacourse.matzip.application.support.ImageUploader;
import com.woowacourse.matzip.domain.image.Image;
import com.woowacourse.matzip.domain.image.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageUploader imageUploader;

    public ImageService(final ImageRepository imageRepository, final ImageUploader imageUploader) {
        this.imageRepository = imageRepository;
        this.imageUploader = imageUploader;
    }

    public ImageResponse uploadImage(final MultipartFile imageFile) {
        Image image = imageUploader.uploadImage(imageFile);
        imageRepository.save(image);
        return new ImageResponse(image.getImageUrl());
    }
}

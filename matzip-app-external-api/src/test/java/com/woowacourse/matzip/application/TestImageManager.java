package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.image.UnusedImage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.woowacourse.matzip.environment.ProfileUtil.TEST;

@Component
@Profile(TEST)
public class TestImageManager implements ImageManager {

    @Override
    public String uploadImage(MultipartFile file) {
        return null;
    }

    @Override
    public void deleteImage(String imageUrl) {

    }

    @Override
    public void deleteImages(List<UnusedImage> unusedImages) {

    }
}

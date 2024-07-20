package com.woowacourse.support;

import com.woowacourse.matzip.application.image.ImageManager;
import com.woowacourse.matzip.domain.image.UnusedImage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.woowacourse.matzip.environment.ProfileUtil.TEST;

@Profile(TEST)
@Component
public class TestImageManager implements ImageManager {

    @Override
    public String uploadImage(final MultipartFile file) {
        return null;
    }

    @Override
    public void deleteImages(final List<UnusedImage> unusedImages) {

    }
}

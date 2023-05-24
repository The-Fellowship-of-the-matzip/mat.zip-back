package com.woowacourse.matzip.presentation;

import com.woowacourse.matzip.application.ImageService;
import com.woowacourse.matzip.application.response.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<ImageResponse> uploadImage(@RequestPart MultipartFile imageFile) throws IOException {
        final ImageResponse imageResponse = imageService.saveImage(imageFile);
        return ResponseEntity.ok(imageResponse);
    }
}

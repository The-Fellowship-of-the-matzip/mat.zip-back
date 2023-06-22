package com.woowacourse.matzip.application.response;

import lombok.Getter;

@Getter
public class ImageUploadResponse {

    private String imageUrl;

    private ImageUploadResponse() {
    }

    public ImageUploadResponse(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

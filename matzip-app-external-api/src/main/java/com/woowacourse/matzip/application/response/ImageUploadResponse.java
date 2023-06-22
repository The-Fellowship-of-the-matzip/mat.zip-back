package com.woowacourse.matzip.application.response;

public class ImageUploadResponse {

    private String imageUrl;

    private ImageUploadResponse() {
    }

    public ImageUploadResponse(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

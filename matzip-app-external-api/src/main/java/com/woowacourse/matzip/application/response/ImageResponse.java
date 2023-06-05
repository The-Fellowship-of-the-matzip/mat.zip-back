package com.woowacourse.matzip.application.response;

public class ImageResponse {

    private String imageUrl;

    private ImageResponse() {
    }

    public ImageResponse(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

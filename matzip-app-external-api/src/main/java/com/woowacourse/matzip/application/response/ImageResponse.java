package com.woowacourse.matzip.application.response;

public class ImageResponse {

    private String imageUrl;

    public ImageResponse() {
    }

    public ImageResponse(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

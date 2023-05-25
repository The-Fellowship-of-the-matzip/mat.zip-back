package com.woowacourse.matzip.application.support;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.woowacourse.matzip.domain.image.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageUploader {

    private static final String IMAGE = "image";
    private final AmazonS3 amazonS3;
    private final String bucketName;

    public ImageUploader(final AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") final String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public Image uploadImage(final MultipartFile imageFile) {
        validateImageFile(imageFile);
        ObjectMetadata objectMetaData = parseObjectMetadata(imageFile);

        String originalFilename = imageFile.getOriginalFilename();
        uploadImageToS3(imageFile, objectMetaData, originalFilename);
        String imagePath = amazonS3.getUrl(bucketName, originalFilename).toString();

        return Image.builder()
                .imageUrl(imagePath)
                .build();
    }

    private void validateImageFile(final MultipartFile imageFile) {
        String contentType = imageFile.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException();
        }
        if (isImageContent(contentType)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isImageContent(final String contentType) {
        return !contentType.contains(IMAGE);
    }

    private ObjectMetadata parseObjectMetadata(final MultipartFile imageFile) {
        long size = imageFile.getSize();
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(imageFile.getContentType());
        objectMetaData.setContentLength(size);
        return objectMetaData;
    }

    private void uploadImageToS3(final MultipartFile imageFile, final ObjectMetadata objectMetaData, final String originalFilename) {
        try {
            amazonS3.putObject(
                    new PutObjectRequest(bucketName, originalFilename, imageFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

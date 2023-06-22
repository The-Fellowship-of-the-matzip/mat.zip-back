package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.ImageUploadResponse;
import com.woowacourse.matzip.domain.image.ImageExtension;
import com.woowacourse.matzip.exception.UploadFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ImageService {

    private static final String EXTENSION_DELIMITER = ".";
    private static final String CLOUDFRONT_URL = "https://image.matzip.today/";

    private final S3Client s3Client;
    private final String bucketName;

    public ImageService(final S3Client s3Client, @Value("${cloud.aws.s3.bucket}") final String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Transactional
    public ImageUploadResponse uploadImage(final MultipartFile file) {
        String extension = validateExtension(file);
        PutObjectRequest request = createRequest(file, extension);
        String key = createKey(extension);
        try {
            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new UploadFailedException();
        }
        return new ImageUploadResponse(CLOUDFRONT_URL + key);
    }

    private String validateExtension(final MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf('.') + 1);
        return ImageExtension.validateExtension(extension);
    }

    private String createKey(final String extension) {
        String uuid = UUID.randomUUID().toString();
        return uuid + EXTENSION_DELIMITER + extension;
    }

    private PutObjectRequest createRequest(final MultipartFile file, final String key) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentLength(file.getSize())
                .build();
    }
}

package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.image.UnusedImage;
import com.woowacourse.matzip.exception.UploadFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.woowacourse.matzip.environment.ProfileUtil.LOCAL;
import static com.woowacourse.matzip.environment.ProfileUtil.PROD;

@Profile({LOCAL, PROD})
@Component
public class S3ImageUploader implements ImageUploader {

    private static final String EXTENSION_DELIMITER = ".";
    private static final String URL_DELIMITER = "/";

    private final S3Client s3Client;
    private final String bucketName;
    private final String cloudFrontUrl;

    public S3ImageUploader(final S3Client s3Client,
                           @Value("${cloud.aws.s3.bucket}") final String bucketName,
                           @Value("${cloud.aws.s3.cloud-front-url}") final String cloudFrontUrl) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.cloudFrontUrl = cloudFrontUrl;
    }

    @Override
    public String uploadImage(final MultipartFile file) {
        String key = createKey(file);
        PutObjectRequest request = createPutRequest(file, key);
        try {
            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new UploadFailedException();
        }

        return cloudFrontUrl + key;
    }

    private PutObjectRequest createPutRequest(final MultipartFile file, final String key) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentLength(file.getSize())
                .build();
    }

    private String createKey(final MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Objects.requireNonNull(fileName);
        String extension = fileName.substring(fileName.lastIndexOf(EXTENSION_DELIMITER + 1));
        String uuid = UUID.randomUUID().toString();
        return uuid + EXTENSION_DELIMITER + extension;
    }

    @Override
    public void deleteImage(final String imageUrl) {
        DeleteObjectRequest request = createDeleteRequest(imageUrl);
        s3Client.deleteObject(request);
    }

    private DeleteObjectRequest createDeleteRequest(final String imageUrl) {
        String key = imageUrl.substring(imageUrl.lastIndexOf(URL_DELIMITER) + 1);
        return DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
    }

    @Override
    public void deleteImages(final List<UnusedImage> unusedImages) {

        List<ObjectIdentifier> identifiers = toIdentifiers(unusedImages);
        s3Client.deleteObjects(createDeleteObjectsRequest(identifiers));
    }

    private List<ObjectIdentifier> toIdentifiers(final List<UnusedImage> unusedImages) {
        return unusedImages.stream()
                .map(UnusedImage::getImageUrl)
                .map(key -> ObjectIdentifier.builder()
                        .key(key)
                        .build())
                .collect(Collectors.toList());
    }

    private DeleteObjectsRequest createDeleteObjectsRequest(final List<ObjectIdentifier> identifiers) {
        return DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(it -> it.objects(identifiers).build())
                .build();
    }
}

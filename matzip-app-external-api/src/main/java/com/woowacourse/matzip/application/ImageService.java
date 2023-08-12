package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.ImageUploadResponse;
import com.woowacourse.matzip.domain.image.ImageExtension;
import com.woowacourse.matzip.domain.image.UnusedImage;
import com.woowacourse.matzip.domain.image.unusedImageRepository;
import com.woowacourse.matzip.exception.UploadFailedException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Transactional(readOnly = true)
public class ImageService {

    private static final String EXTENSION_DELIMITER = ".";
    private static final String CLOUDFRONT_URL = "https://image.matzip.today/";

    private final S3Client s3Client;
    private final String bucketName;
    private final unusedImageRepository unusedImageRepository;

    public ImageService(final S3Client s3Client, @Value("${cloud.aws.s3.bucket}") final String bucketName, unusedImageRepository unusedImageRepository) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.unusedImageRepository = unusedImageRepository;
    }

    @Transactional
    public ImageUploadResponse uploadImage(final MultipartFile file) {
        String extension = validateExtension(file);
        String key = createKey(extension);
        PutObjectRequest request = createPutRequest(file, key);
        try {
            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new UploadFailedException();
        }
        unusedImageRepository.save(
                UnusedImage.builder()
                           .key(key)
                           .build()
        );
        return new ImageUploadResponse(CLOUDFRONT_URL + key);
    }

    private String validateExtension(final MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = Objects.requireNonNull(originalFilename)
                                  .substring(originalFilename.lastIndexOf('.') + 1);
        return ImageExtension.validateExtension(extension);
    }

    private String createKey(final String extension) {
        String uuid = UUID.randomUUID().toString();
        return uuid + EXTENSION_DELIMITER + extension;
    }

    private PutObjectRequest createPutRequest(final MultipartFile file, final String key) {
        return PutObjectRequest.builder()
                               .bucket(bucketName)
                               .key(key)
                               .contentLength(file.getSize())
                               .build();
    }

    @Transactional
    public void deleteUsingImage(final String imageUrl) {
        String key = imageUrl.substring(CLOUDFRONT_URL.length());
        unusedImageRepository.deleteByKey(key);
    }

    @Transactional
    public void deleteImageWhenReviewDeleted(final String imageUrl) {
        String key = imageUrl.substring(CLOUDFRONT_URL.length());
        DeleteObjectRequest request = createDeleteRequest(key);
        s3Client.deleteObject(request);
    }

    private DeleteObjectRequest createDeleteRequest(final String key) {
        return DeleteObjectRequest.builder()
                                  .bucket(bucketName)
                                  .key(key)
                                  .build();
    }

    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void deleteUnusedImages() {
        LocalDateTime deleteBoundary = LocalDateTime.now().minusDays(1L);
        List<UnusedImage> unusedImages = unusedImageRepository.findAllByCreatedAtBefore(deleteBoundary);
        unusedImageRepository.deleteAllByCreatedAtBefore(deleteBoundary);
        List<ObjectIdentifier> identifiers = toIdentifiers(unusedImages);
        s3Client.deleteObjects(createDeleteObjectsRequest(identifiers));
    }

    private List<ObjectIdentifier> toIdentifiers(final List<UnusedImage> unusedImages) {
        return unusedImages.stream()
                           .map(UnusedImage::getKey)
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

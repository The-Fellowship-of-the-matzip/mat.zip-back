package com.woowacourse.matzip.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.woowacourse.matzip.application.response.ImageResponse;
import com.woowacourse.matzip.domain.image.Image;
import com.woowacourse.matzip.domain.image.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;
    private final AmazonS3 amazonS3;
    private final String bucket;

    public ImageService(final ImageRepository imageRepository,
                        final AmazonS3 amazonS3,
                        @Value("${cloud.aws.s3.bucket}") String bucket) {
        this.imageRepository = imageRepository;
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    public ImageResponse saveImage(MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        final long size = imageFile.getSize();
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(imageFile.getContentType());
        objectMetaData.setContentLength(size);

        amazonS3.putObject(
                new PutObjectRequest(bucket, originalFilename, imageFile.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        String imagePath = amazonS3.getUrl(bucket, originalFilename).toString();
        imageRepository.save(Image.builder()
                .imageUrl(imagePath)
                .build());
        return new ImageResponse(imagePath);
    }
}

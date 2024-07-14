package com.woowacourse.matzip.domain.image;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "unused_image")
@EntityListeners(AuditingEntityListener.class)
@Getter
public class UnusedImage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    protected UnusedImage() {
    }

    @Builder
    public UnusedImage(final Long id, final String imageUrl, final LocalDateTime createdAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
}

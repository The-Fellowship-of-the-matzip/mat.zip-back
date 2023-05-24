package com.woowacourse.matzip.domain.image;

import lombok.Builder;
import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    protected Image() {
    }

    @Builder
    public Image(final Long id, final String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }
}

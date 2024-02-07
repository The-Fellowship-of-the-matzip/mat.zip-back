package com.woowacourse.matzip.domain.bookmark;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "bookmark")
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public Bookmark() {
    }

    @Builder
    public Bookmark(final Long id, final Member member, final Restaurant restaurant) {
        this.id = id;
        this.member = member;
        this.restaurant = restaurant;
    }
}

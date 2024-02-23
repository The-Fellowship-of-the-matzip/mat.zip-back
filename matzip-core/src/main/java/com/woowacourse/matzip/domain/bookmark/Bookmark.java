package com.woowacourse.matzip.domain.bookmark;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

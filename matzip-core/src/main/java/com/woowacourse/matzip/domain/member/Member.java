package com.woowacourse.matzip.domain.member;

import com.woowacourse.matzip.domain.bookmark.Bookmark;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_id", nullable = false, unique = true)
    private String githubId;

    @Column(nullable = false)
    private String username;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    protected Member() {
    }

    @Builder
    public Member(final Long id, final String githubId, final String username, final String profileImage) {
        this.id = id;
        this.githubId = githubId;
        this.username = username;
        this.profileImage = profileImage;
    }

    public void updateUsername(final String username) {
        if (Objects.nonNull(username)) {
            this.username = username;
        }
    }

    public void updateProfileImage(final String profileImage) {
        if (Objects.nonNull(profileImage)) {
            this.profileImage = profileImage;
        }
    }

    public boolean isSameGithubId(final String githubId) {
        return this.githubId.equals(githubId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

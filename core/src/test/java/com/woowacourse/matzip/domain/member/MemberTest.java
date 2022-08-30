package com.woowacourse.matzip.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class MemberTest {

    @Test
    void 이름을_업데이트한다() {
        Member member = new Member(1L, "1", "huni", "image.png");
        member.updateUsername("huno");
        assertThat(member.getUsername()).isEqualTo("huno");
    }

    @Test
    void 이미지를_업데이트한다() {
        Member member = new Member(1L, "1", "huni", "image.png");
        member.updateProfileImage("update.png");
        assertThat(member.getProfileImage()).isEqualTo("update.png");
    }

    @Test
    void github_아이디가_같다() {
        Member member = new Member(1L, "1", "huni", "image.png");
        assertThat(member.isSameGithubId("1")).isTrue();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "2")
    void github_아이디가_null이다(String githubId) {
        Member member = new Member(1L, "1", "huni", "image.png");
        assertThat(member.isSameGithubId(githubId)).isFalse();
    }
}

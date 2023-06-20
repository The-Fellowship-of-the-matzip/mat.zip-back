package com.woowacourse.matzip.application;

import static com.woowacourse.matzip.MemberFixtures.HUNI;
import static com.woowacourse.matzip.MemberFixtures.ORI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberServiceTest extends ServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 멤버가_존재하면_업데이트한다() {
        Member persistMember = memberRepository.save(ORI.toMember());
        GithubProfileResponse githubProfileResponse =
                new GithubProfileResponse(ORI.getGithubId(), HUNI.getUsername(), HUNI.getProfileImage());

        memberService.createOrUpdate(githubProfileResponse);
        Member foundMember = memberRepository.findMemberByGithubId(persistMember.getGithubId())
                .orElse(null);
        assertAll(
                () -> assertThat(foundMember).isNotNull(),
                () -> assertThat(persistMember.getId()).isEqualTo(foundMember.getId()),
                () -> assertThat(foundMember.getUsername()).isEqualTo(HUNI.getUsername()),
                () -> assertThat(foundMember.getProfileImage()).isEqualTo(HUNI.getProfileImage())
        );
    }

    @Test
    void 멤버가_없으면_생성한다() {
        GithubProfileResponse githubProfileResponse =
                new GithubProfileResponse(HUNI.getGithubId(), HUNI.getUsername(), HUNI.getProfileImage());

        memberService.createOrUpdate(githubProfileResponse);
        assertThat(memberRepository.findMemberByGithubId(HUNI.getGithubId()).isPresent()).isTrue();
    }
}

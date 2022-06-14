package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.HUNI;
import static com.woowacourse.auth.support.GithubResponseFixtures.ORI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.support.SpringServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringServiceTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 멤버가_존재하면_업데이트한다() {
        Member persistMember = memberRepository.save(HUNI.toMember());
        GithubProfileResponse githubProfileResponse =
                new GithubProfileResponse(HUNI.getGithubId(), ORI.getUsername(), ORI.getProfileImage());

        memberService.createOrUpdate(githubProfileResponse);
        Member foundMember = memberRepository.findMemberByGithubId(persistMember.getGithubId())
                .orElse(null);
        assertAll(
                () -> assertThat(foundMember).isNotNull(),
                () -> assertThat(persistMember.getId()).isEqualTo(foundMember.getId()),
                () -> assertThat(foundMember.getUsername()).isEqualTo(ORI.getUsername()),
                () -> assertThat(foundMember.getProfileImage()).isEqualTo(ORI.getProfileImage())
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

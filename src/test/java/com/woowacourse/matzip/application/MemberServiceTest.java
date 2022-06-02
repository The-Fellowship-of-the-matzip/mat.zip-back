package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.HUNI;
import static org.assertj.core.api.Assertions.assertThat;

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
    void 멤버가_존재하면_가져온다() {
        Member persistMember = memberRepository.save(HUNI.toMember());
        GithubProfileResponse githubProfileResponse =
                new GithubProfileResponse(HUNI.getGithubId(), HUNI.getUsername(), HUNI.getProfileImage());

        Member foundMember = memberService.createOrFind(githubProfileResponse);
        assertThat(persistMember).isEqualTo(foundMember);
    }

    @Test
    void 멤버가_없으면_생성한다() {
        GithubProfileResponse githubProfileResponse =
                new GithubProfileResponse(HUNI.getGithubId(), HUNI.getUsername(), HUNI.getProfileImage());

        Member persistMember = memberService.createOrFind(githubProfileResponse);
        assertThat(memberRepository.findById(persistMember.getId()).isPresent()).isTrue();
    }

}

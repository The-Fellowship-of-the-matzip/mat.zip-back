package com.woowacourse.matzip.application;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createOrFind(final GithubProfileResponse githubProfileResponse) {
        return memberRepository.findMemberByGithubId(githubProfileResponse.getGithubId())
                .orElseGet(() -> memberRepository.save(githubProfileResponse.toMember()));
    }
}

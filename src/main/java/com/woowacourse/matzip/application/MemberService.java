package com.woowacourse.matzip.application;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import com.woowacourse.matzip.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createOrUpdate(final GithubProfileResponse githubProfileResponse) {
        memberRepository.findMemberByGithubId(githubProfileResponse.getGithubId())
                .ifPresentOrElse(member -> {
                    member.updateUsername(githubProfileResponse.getUsername());
                    member.updateProfileImage(githubProfileResponse.getProfileImage());
                }, () -> memberRepository.save(githubProfileResponse.toMember()));
    }
}

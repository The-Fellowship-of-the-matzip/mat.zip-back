package com.woowacourse.auth.application;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import com.woowacourse.auth.application.dto.TokenResponse;
import com.woowacourse.matzip.application.MemberService;
import com.woowacourse.matzip.domain.member.Member;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final GithubOauthClient githubOauthClient;

    public AuthService(JwtTokenProvider jwtTokenProvider,
                       MemberService memberService,
                       GithubOauthClient githubOauthClient) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.githubOauthClient = githubOauthClient;
    }

    public TokenResponse createToken(final String code) {
        String accessToken = githubOauthClient.getGithubAccessToken(code);
        GithubProfileResponse githubProfile = githubOauthClient.getGithubProfile(accessToken);
        Member member = memberService.createOrFind(githubProfile);
        return new TokenResponse(jwtTokenProvider.createToken(member.getGithubId()));
    }
}

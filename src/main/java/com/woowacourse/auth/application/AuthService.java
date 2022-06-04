package com.woowacourse.auth.application;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import com.woowacourse.auth.application.dto.TokenResponse;
import com.woowacourse.matzip.application.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final GithubOauthClient githubOauthClient;

    public AuthService(final JwtTokenProvider jwtTokenProvider,
                       final MemberService memberService,
                       final GithubOauthClient githubOauthClient) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.githubOauthClient = githubOauthClient;
    }

    public TokenResponse createToken(final String code) {
        String accessToken = githubOauthClient.getGithubAccessToken(code);
        GithubProfileResponse githubProfile = githubOauthClient.getGithubProfile(accessToken);
        memberService.createOrUpdate(githubProfile);
        return new TokenResponse(jwtTokenProvider.createToken(githubProfile.getGithubId()));
    }
}

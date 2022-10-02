package com.woowacourse.auth.support;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import com.woowacourse.matzip.MemberFixtures;
import java.util.Arrays;

public class GithubResponseTransferUtils {

    public static String parseToAccessToken(final String code) {
        return Arrays.stream(MemberFixtures.values())
                .filter(value -> value.getCode().equals(code))
                .findFirst()
                .map(MemberFixtures::getAccessToken)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static GithubProfileResponse findResponseByAccessToken(final String accessToken) {
        return Arrays.stream(MemberFixtures.values())
                .filter(value -> value.getAccessToken().equals(accessToken))
                .findFirst()
                .map(GithubResponseTransferUtils::from)
                .orElseThrow(IllegalArgumentException::new);
    }

    private static GithubProfileResponse from(final MemberFixtures memberFixtures) {
        return new GithubProfileResponse(memberFixtures.getGithubId(), memberFixtures.getUsername(), memberFixtures.getProfileImage());
    }
}

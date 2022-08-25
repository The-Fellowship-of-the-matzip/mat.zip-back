package com.woowacourse.auth.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.HUNI;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class GithubOauthClientTest {

    @Autowired
    private GithubOauthClient githubOauthClient;

    @Test
    void 깃헙_access_token을_요청한다() {
        String githubAccessToken = githubOauthClient.getGithubAccessToken("1");
        assertThat(githubAccessToken).isEqualTo("access_token_1");
    }

    @Test
    void 깃헙_profile을_요청한다() {
        GithubProfileResponse profile = githubOauthClient.getGithubProfile("access_token_1");

        assertThat(profile).usingRecursiveComparison()
                .isEqualTo(new GithubProfileResponse(HUNI.getGithubId(), HUNI.getUsername(), HUNI.getProfileImage()));
    }
}

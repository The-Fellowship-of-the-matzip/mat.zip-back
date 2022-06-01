package com.woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("fake")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class GithubOauthClientTest {

    @Autowired
    private GithubOauthClient githubOauthClient;

    @Test
    void 깃헙_access_token을_요청한다() {
        String githubAccessToken = githubOauthClient.getGithubAccessToken("1");
        assertThat(githubAccessToken).isEqualTo("access_token_1");
    }
}

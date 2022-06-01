package com.woowacourse.auth.support;

import com.woowacourse.auth.application.dto.GithubTokenRequest;
import com.woowacourse.auth.application.dto.GithubTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeGithubController {

    @PostMapping("/login/oauth/access_token")
    public ResponseEntity<GithubTokenResponse> getAccessToken(@RequestBody GithubTokenRequest githubTokenRequest) {
        String accessToken = GithubResponseFixtures.findResponseByCode(githubTokenRequest.getCode()).getAccessToken();
        return ResponseEntity.ok(new GithubTokenResponse(accessToken));
    }
}
package com.woowacourse.auth.support;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import com.woowacourse.auth.application.dto.GithubTokenRequest;
import com.woowacourse.auth.application.dto.GithubTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeGithubController {

    @PostMapping("/login/oauth/access_token")
    public ResponseEntity<GithubTokenResponse> getAccessToken(@RequestBody GithubTokenRequest githubTokenRequest) {
        String accessToken = GithubResponseTransferUtils.parseToAccessToken(githubTokenRequest.getCode());
        return ResponseEntity.ok(new GithubTokenResponse(accessToken));
    }

    @GetMapping("/user")
    public ResponseEntity<GithubProfileResponse> getProfile(@RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.split(" ")[1];
        return ResponseEntity.ok(GithubResponseTransferUtils.findResponseByAccessToken(accessToken));
    }
}

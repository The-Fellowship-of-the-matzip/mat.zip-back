package com.woowacourse.auth.application;

import com.woowacourse.auth.application.dto.GithubProfileResponse;
import com.woowacourse.auth.application.dto.GithubTokenRequest;
import com.woowacourse.auth.application.dto.GithubTokenResponse;
import com.woowacourse.auth.exception.GithubAccessException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubOauthClient {

    private final String clientId;

    private final String clientSecret;

    private final String tokenUrl;

    private final String profileUrl;

    public GithubOauthClient(@Value("${github.client.id}") final String clientId,
                             @Value("${github.client.secret}") final String clientSecret,
                             @Value("${github.url.access-token}") final String tokenUrl,
                             @Value("${github.url.profile}") final String profileUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenUrl = tokenUrl;
        this.profileUrl = profileUrl;
    }

    public String getGithubAccessToken(final String code) {
        GithubTokenRequest githubTokenRequest = new GithubTokenRequest(clientId, clientSecret, code);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<GithubTokenRequest> httpEntity = new HttpEntity<>(githubTokenRequest, httpHeaders);

        GithubTokenResponse response = new RestTemplate()
                .exchange(tokenUrl, HttpMethod.POST, httpEntity, GithubTokenResponse.class)
                .getBody();

        if (Objects.isNull(response)) {
            throw new GithubAccessException();
        }
        return response.getAccessToken();
    }

    public GithubProfileResponse getGithubProfile(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + accessToken);

        HttpEntity httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate
                    .exchange(profileUrl, HttpMethod.GET, httpEntity, GithubProfileResponse.class)
                    .getBody();
        } catch (HttpClientErrorException e) {
            throw new GithubAccessException();
        }
    }
}

package com.woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.auth.exception.InvalidTokenException;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private static final String SECRET_KEY = "dasdc338hfhghsn21sdf1jvnu4ascasv21908fyhas2a";
    private static final int VALIDITY_IN_MILLISECONDS = 1000000;

    @Test
    void 토큰을_생성한다() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, VALIDITY_IN_MILLISECONDS);
        String token = jwtTokenProvider.createToken("1");
        assertThat(token).isNotNull();
    }

    @Test
    void 토큰_페이로드를_가져온다() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, VALIDITY_IN_MILLISECONDS);
        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo("1");
    }

    @Test
    void 기간이_만료되면_에러가_발생한다() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, 0);
        String token = jwtTokenProvider.createToken("1");

        assertThatThrownBy(() -> jwtTokenProvider.getPayload(token))
                .isInstanceOf(InvalidTokenException.class);
    }
}

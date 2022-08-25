package com.woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorizationExtractorTest {

    private static final String AUTHORIZATION_HEADER_TYPE = "Authorization";

    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    @DisplayName("Authorization 헤더에 값이 존재하지 않음")
    void extractNothing_empty() {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn(null);

        assertThat(AuthorizationExtractor.extract(request)).isEmpty();
    }

    @Test
    @DisplayName("헤더에 값이 존재하지만 type이 다름")
    void extractDismatchType_empty() {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn("Error jwt.token.here");

        assertThat(AuthorizationExtractor.extract(request)).isEmpty();
    }

    @Test
    @DisplayName("토큰값이 존재하지 않음")
    void extractEmptyToken_empty() {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn("Bearer");

        assertThat(AuthorizationExtractor.extract(request)).isEmpty();
    }

    @Test
    @DisplayName("정상적으로 extract한 토큰값 반환")
    void extract() {
        when(request.getHeader(AUTHORIZATION_HEADER_TYPE)).thenReturn("Bearer jwt.token.here");

        assertThat(AuthorizationExtractor.extract(request)).isEqualTo(Optional.of("jwt.token.here"));
    }
}

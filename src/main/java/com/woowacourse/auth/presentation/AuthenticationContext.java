package com.woowacourse.auth.presentation;

import com.woowacourse.auth.exception.AuthenticationContextException;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticationContext {

    private String authority;

    public String getAuthority() {
        if (Objects.isNull(authority)) {
            throw new AuthenticationContextException();
        }
        return authority;
    }

    public void setAuthority(final String authority) {
        if (Objects.nonNull(this.authority)) {
            throw new AuthenticationContextException();
        }
        this.authority = authority;
    }
}

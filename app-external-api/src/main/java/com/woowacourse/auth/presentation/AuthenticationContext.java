package com.woowacourse.auth.presentation;

import com.woowacourse.auth.exception.AuthenticationContextException;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticationContext {

    private String principal;

    public String getPrincipal() {
        if (Objects.isNull(principal)) {
            throw new AuthenticationContextException();
        }
        return principal;
    }

    public void setPrincipal(final String principal) {
        if (Objects.nonNull(this.principal)) {
            throw new AuthenticationContextException();
        }
        this.principal = principal;
    }
}

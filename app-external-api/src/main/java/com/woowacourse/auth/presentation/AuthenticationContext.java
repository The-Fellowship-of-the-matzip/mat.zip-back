package com.woowacourse.auth.presentation;

import com.woowacourse.auth.exception.AuthenticationContextException;
import com.woowacourse.auth.support.MemberStatus;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticationContext {

    private String principal;
    private MemberStatus memberStatus;

    public String getPrincipal() {
        if (Objects.isNull(this.principal)) {
            throw new AuthenticationContextException();
        }
        return principal;
    }

    public void setAuthenticationMember(final String principal) {
        setPrincipal(principal);
        setMemberStatus(MemberStatus.MEMBER);
    }

    public void setNotLogin() {
        setMemberStatus(MemberStatus.ANONYMOUS);
    }

    private void setPrincipal(final String principal) {
        if (Objects.nonNull(this.principal)) {
            throw new AuthenticationContextException();
        }
        this.principal = principal;
    }

    private void setMemberStatus(final MemberStatus memberStatus) {
        if (Objects.nonNull(this.memberStatus)) {
            throw new AuthenticationContextException();
        }
        this.memberStatus = memberStatus;
    }

    public boolean hasAnonymous() {
        if (Objects.isNull(memberStatus)) {
            throw new AuthenticationContextException();
        }
        return memberStatus.isAnonymous();
    }
}

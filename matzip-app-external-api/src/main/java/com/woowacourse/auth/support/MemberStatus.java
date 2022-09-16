package com.woowacourse.auth.support;

public enum MemberStatus {

    MEMBER,
    ANONYMOUS;

    public boolean isAnonymous() {
        return this == ANONYMOUS;
    }
}

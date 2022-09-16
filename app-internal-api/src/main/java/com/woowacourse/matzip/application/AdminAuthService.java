package com.woowacourse.matzip.application;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    private final String username;
    private final String password;

    public AdminAuthService(@Value("${admin.username}") final String username,
                            @Value("${admin.password}") final String password) {
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(final String username, final String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        return this.username.equals(username) && this.password.equals(password);
    }
}

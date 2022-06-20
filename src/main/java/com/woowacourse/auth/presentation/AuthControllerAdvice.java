package com.woowacourse.auth.presentation;

import com.woowacourse.auth.exception.GithubAccessException;
import com.woowacourse.auth.exception.InvalidTokenException;
import com.woowacourse.auth.exception.TokenNotFoundException;
import com.woowacourse.support.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(GithubAccessException.class)
    public ResponseEntity<ErrorResponse> githubAccessExceptionHandler(final GithubAccessException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
            InvalidTokenException.class,
            TokenNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> tokenExceptionHandler(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.from(e));
    }
}

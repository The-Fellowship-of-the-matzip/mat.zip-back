package com.woowacourse.auth.presentation;

import com.woowacourse.auth.exception.GithubAccessException;
import com.woowacourse.support.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(GithubAccessException.class)
    public ResponseEntity<ErrorResponse> githubAccessExceptionHandler(final GithubAccessException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }
}

package com.woowacourse.auth.presentation;

import com.woowacourse.auth.exception.ErrorResponse;
import com.woowacourse.auth.exception.GithubAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(GithubAccessException.class)
    public ResponseEntity<ErrorResponse> githubAccessExceptionHandler(final GithubAccessException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}

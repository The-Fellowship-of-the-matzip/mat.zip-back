package com.woowacourse.support.exception;

import com.woowacourse.auth.exception.GithubAccessException;
import com.woowacourse.auth.exception.InvalidTokenException;
import com.woowacourse.auth.exception.TokenNotFoundException;
import com.woowacourse.matzip.exception.CampusNotFoundException;
import com.woowacourse.matzip.exception.InvalidCategoryException;
import com.woowacourse.matzip.exception.InvalidReviewException;
import com.woowacourse.matzip.exception.InvalidSortConditionException;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({
            GithubAccessException.class
    })
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

    @ExceptionHandler({
            CampusNotFoundException.class,
            MemberNotFoundException.class,
            RestaurantNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
            InvalidCategoryException.class,
            InvalidReviewException.class,
            InvalidSortConditionException.class
    })
    public ResponseEntity<ErrorResponse> businessExceptionHandler(final RuntimeException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponse> springValidationExceptionHandler(final MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<ErrorResponse> internalExceptionHandler() {
        return ResponseEntity.internalServerError().body(ErrorResponse.from("서버에 문제가 발생했습니다."));
    }
}

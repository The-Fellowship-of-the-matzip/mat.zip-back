package com.woowacourse.support.exception;

import com.woowacourse.auth.exception.GithubAccessException;
import com.woowacourse.auth.exception.InvalidTokenException;
import com.woowacourse.auth.exception.TokenNotFoundException;
import com.woowacourse.matzip.exception.AlreadyBookmarkedException;
import com.woowacourse.matzip.exception.AlreadyRegisteredException;
import com.woowacourse.matzip.exception.BookmarkNotFoundException;
import com.woowacourse.matzip.exception.CampusNotFoundException;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.exception.InvalidCategoryException;
import com.woowacourse.matzip.exception.InvalidLengthException;
import com.woowacourse.matzip.exception.InvalidReviewException;
import com.woowacourse.matzip.exception.InvalidSortConditionException;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.RestaurantNotFoundException;
import com.woowacourse.matzip.exception.ReviewNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
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

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenException(final ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
            CampusNotFoundException.class,
            MemberNotFoundException.class,
            RestaurantNotFoundException.class,
            ReviewNotFoundException.class,
            RestaurantNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
            InvalidCategoryException.class,
            InvalidReviewException.class,
            InvalidSortConditionException.class,
            InvalidLengthException.class,
            AlreadyRegisteredException.class,
            AlreadyBookmarkedException.class,
            BookmarkNotFoundException.class
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
    public ResponseEntity<ErrorResponse> internalExceptionHandler(final Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(ErrorResponse.from("서버에 문제가 발생했습니다."));
    }
}

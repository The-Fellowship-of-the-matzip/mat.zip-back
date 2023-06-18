package com.woowacourse.matzip.presentation;

import com.woowacourse.auth.support.AuthenticationPrincipal;
import com.woowacourse.matzip.application.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmarks/restaurants/{restaurantId}")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(final BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping
    public ResponseEntity<Void> createBookmark(@PathVariable final Long restaurantId,
                                               @AuthenticationPrincipal final String githubId) {
        bookmarkService.createBookmark(githubId, restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBookmark(@PathVariable final Long restaurantId,
                                               @AuthenticationPrincipal final String githubId) {
        bookmarkService.deleteBookmark(githubId, restaurantId);
        return ResponseEntity.noContent().build();
    }
}

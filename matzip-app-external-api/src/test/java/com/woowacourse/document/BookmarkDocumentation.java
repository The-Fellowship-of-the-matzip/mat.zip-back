package com.woowacourse.document;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class BookmarkDocumentation extends Documentation {

    @Test
    void 북마크_등록() {
        doNothing().when(bookmarkService).createBookmark(anyString(), anyLong());

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .when().post("/api/bookmarks/restaurants/1")
                .then().log().all()
                .apply(document("bookmarks/create"))
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 북마크_취소() {
        doNothing().when(bookmarkService).deleteBookmark(anyString(), anyLong());

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer jwt.token.here")
                .when().delete("/api/bookmarks/restaurants/1")
                .then().log().all()
                .apply(document("bookmarks/delete"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}

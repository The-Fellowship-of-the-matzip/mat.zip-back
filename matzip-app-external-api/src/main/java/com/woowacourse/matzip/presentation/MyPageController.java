package com.woowacourse.matzip.presentation;

import com.woowacourse.auth.support.AuthenticationPrincipal;
import com.woowacourse.matzip.application.MyPageService;
import com.woowacourse.matzip.application.response.MyReviewsResponse;
import com.woowacourse.matzip.application.response.ProfileResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    public MyPageController(final MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getMyProfile(@AuthenticationPrincipal final String githubId) {
        return ResponseEntity.ok(myPageService.findProfile(githubId));
    }

    @GetMapping("/reviews")
    public ResponseEntity<MyReviewsResponse> findMyReviews(final Pageable pageable,
                                                           @AuthenticationPrincipal final String githubId) {
        return ResponseEntity.ok(myPageService.findPageByMember(githubId, pageable));
    }
}

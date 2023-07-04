package com.woowacourse.matzip.application;

import static com.woowacourse.matzip.MemberFixtures.HUNI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.application.response.MyReviewsResponse;
import com.woowacourse.matzip.application.response.ProfileResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.review.DefaultReviewInfo;
import com.woowacourse.matzip.domain.review.MemberReviewInfo;
import com.woowacourse.matzip.domain.review.Review;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.support.SpringServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@SpringServiceTest
class MyPageServiceTest {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 내_정보를_반환한다() {
        Member member = memberRepository.save(HUNI.toMember());
        리뷰_작성(member);

        ProfileResponse profileResponse = myPageService.findProfile(member.getGithubId());
        MemberReviewInfo memberReviewInfo = reviewRepository.findMemberReviewInfoByMemberId(member.getId())
                .orElse(DefaultReviewInfo.getInstance());

        assertAll(
                () -> assertThat(profileResponse.getUsername()).isEqualTo(member.getUsername()),
                () -> assertThat(profileResponse.getProfileImage()).isEqualTo(member.getProfileImage()),
                () -> assertThat(profileResponse.getReviewCount()).isEqualTo(memberReviewInfo.getReviewCount()),
                () -> assertThat(profileResponse.getAverageRating()).isEqualTo(memberReviewInfo.getAverageRating())
        );
    }

    @Test
    void 내_리뷰를_조회한다() {
        Member member = memberRepository.save(HUNI.toMember());
        for (int i = 0; i < 5; i++) {
            리뷰_작성(member);
        }

        MyReviewsResponse page1 = myPageService.findPageByMember(member.getGithubId(), PageRequest.of(0, 2));
        MyReviewsResponse page2 = myPageService.findPageByMember(member.getGithubId(), PageRequest.of(1, 3));

        assertAll(
                () -> assertThat(page1.getReviews()).hasSize(2),
                () -> assertThat(page1.isHasNext()).isTrue(),
                () -> assertThat(page2.getReviews()).hasSize(2),
                () -> assertThat(page2.isHasNext()).isFalse()
        );
    }

    @Test
    void 리뷰를_작성하지_않은_멤버의_리뷰정보_조회() {
        Member member = memberRepository.save(HUNI.toMember());

        ProfileResponse profile = myPageService.findProfile(member.getGithubId());

        assertAll(
                () -> assertThat(profile.getReviewCount()).isZero(),
                () -> assertThat(profile.getAverageRating()).isZero()
        );
    }

    private void 리뷰_작성(Member member) {
        reviewRepository.save(new Review(null, member, 1L, "맛있어요", 5, "메뉴", null, null));
    }
}

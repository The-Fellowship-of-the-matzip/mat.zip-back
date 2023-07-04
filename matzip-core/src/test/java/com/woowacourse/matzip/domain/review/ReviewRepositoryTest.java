package com.woowacourse.matzip.domain.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
@EnableJpaAuditing
class ReviewRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 페이징값을_반환한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        for (int i = 1; i <= 20; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member))
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(4)
                    .menu("족발" + i)
                    .build());
        }
        Slice<Review> firstReviewPage = reviewRepository.findPageByRestaurantIdOrderByIdDesc(1L,
                PageRequest.of(0, 5));
        Slice<Review> secondReviewPage = reviewRepository.findPageByRestaurantIdOrderByIdDesc(1L,
                PageRequest.of(1, 5));

        assertAll(
                () -> assertThat(firstReviewPage).hasSize(5),
                () -> assertThat(firstReviewPage.getContent().get(0).getMenu()).isEqualTo("족발20"),
                () -> assertThat(secondReviewPage).hasSize(5),
                () -> assertThat(secondReviewPage.getContent().get(0).getMenu()).isEqualTo("족발15")
        );
    }

    @Test
    void 평균_별점을_반환한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        for (int i = 1; i <= 10; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member))
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(4)
                    .menu("족발" + i)
                    .build());
        }
        for (int i = 11; i <= 20; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member))
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(5)
                    .menu("족발" + i)
                    .build());
        }

        double average = reviewRepository.findAverageRatingUsingRestaurantId(1L)
                .orElse(0.0);

        assertThat(average).isEqualTo(4.5);
    }

    @Test
    void 리뷰_저장_시_생성시간이_추가된다() {
        LocalDateTime currentTime = LocalDateTime.now();

        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        Review review = reviewRepository.save(Review.builder()
                .member(memberRepository.save(member))
                .restaurantId(1L)
                .content("맛있어요")
                .rating(5)
                .menu("족발")
                .build());

        assertThat(review.getCreatedAt()).isAfter(currentTime);
    }

    @Test
    void 유저들의_리뷰_개수를_조회한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        Member member2 = Member.builder()
                .githubId("githubId2")
                .username("username2")
                .profileImage("url2")
                .build();
        for (int i = 1; i <= 10; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member))
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(4)
                    .menu("족발" + i)
                    .build());
        }
        for (int i = 1; i <= 5; i++) {
            reviewRepository.save(Review.builder()
                    .member(memberRepository.save(member2))
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(4)
                    .menu("족발" + i)
                    .build());
        }
        var reviewCountByMemberIds = reviewRepository.findMemberReviewInfosByMemberIds(
                List.of(member.getId(), member2.getId()));

        assertAll(
                () -> assertThat(reviewCountByMemberIds.get(0).getReviewCount()).isEqualTo(10),
                () -> assertThat(reviewCountByMemberIds.get(1).getReviewCount()).isEqualTo(5)
        );
    }

    @Test
    void 리뷰가_없는_멤버의_리뷰정보를_조회한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        memberRepository.save(member);

        var reviewInfosByMemberIds = reviewRepository.findMemberReviewInfosByMemberIds(List.of(member.getId()));

        assertThat(reviewInfosByMemberIds)
                .isEmpty();
    }

    @Test
    void 리뷰개수_조회시_빈_인자를_받는_경우() {
        var reviewCountByMemberIds = reviewRepository.findMemberReviewInfosByMemberIds(List.of());

        assertThat(reviewCountByMemberIds).isEmpty();
    }

    @Test
    void 리뷰_작성자의_평균_별점을_조회한다() {
        Member member = memberRepository.save(Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build());

        reviewRepository.save(Review.builder()
                .member(memberRepository.save(member))
                .restaurantId(1L)
                .content("맛있어요")
                .rating(4)
                .menu("족발")
                .build());

        reviewRepository.save(Review.builder()
                .member(memberRepository.save(member))
                .restaurantId(1L)
                .content("맛있어요")
                .rating(1)
                .menu("족발2")
                .build());

        var memberReviewInfosByMemberIds = reviewRepository.findMemberReviewInfosByMemberIds(List.of(member.getId()));

        assertThat(memberReviewInfosByMemberIds.get(0).getAverageRating()).isEqualTo(2.5d);
    }

    @Test
    void 멤버의_리뷰를_조회한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
        memberRepository.save(member);

        for (int i = 1; i <= 20; i++) {
            reviewRepository.save(Review.builder()
                    .member(member)
                    .restaurantId(1L)
                    .content("맛있어요")
                    .rating(4)
                    .menu("족발" + i)
                    .build());
        }

        Slice<Review> firstMyReviewPage = reviewRepository.findPageByMemberOrderByIdDesc(member, PageRequest.of(0, 5));
        Slice<Review> secondMyReviewPage = reviewRepository.findPageByMemberOrderByIdDesc(member, PageRequest.of(1, 5));

        assertAll(
                () -> assertThat(firstMyReviewPage).hasSize(5),
                () -> assertThat(firstMyReviewPage.getContent().get(0).getMenu()).isEqualTo("족발20"),
                () -> assertThat(secondMyReviewPage).hasSize(5),
                () -> assertThat(secondMyReviewPage.getContent().get(0).getMenu()).isEqualTo("족발15")
        );
    }

    @Test
    void 리뷰가_없는_멤버의_리뷰정보_조회() {
        Member member = memberRepository.save(Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build());

        Optional<MemberReviewInfo> memberReviewInfoByMemberId = reviewRepository.findMemberReviewInfoByMemberId(member.getId());

        assertThat(memberReviewInfoByMemberId).isNotNull();
    }
}

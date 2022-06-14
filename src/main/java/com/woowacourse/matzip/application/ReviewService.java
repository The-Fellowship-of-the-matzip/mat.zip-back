package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.review.Review;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public ReviewService(final ReviewRepository reviewRepository, final MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createReview(final String githubId, final Long restaurantId,
                             final ReviewCreateRequest reviewCreateRequest) {
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        Review review = reviewCreateRequest.toReviewWithMemberAndRestaurantId(member, restaurantId);
        reviewRepository.save(review);
    }
}

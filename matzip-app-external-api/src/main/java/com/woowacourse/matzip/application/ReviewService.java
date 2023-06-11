package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.ReviewResponse;
import com.woowacourse.matzip.application.response.ReviewsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.review.Review;
import com.woowacourse.matzip.domain.review.ReviewCountByMemberIdsDto;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.ReviewNotFoundException;
import com.woowacourse.matzip.presentation.request.ReviewCreateRequest;
import com.woowacourse.matzip.presentation.request.ReviewUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private static final Long ZERO_REVIEW = 0L;

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

    public ReviewsResponse findPageByRestaurantId(final String githubId, final Long restaurantId,
                                                  final Pageable pageable) {
        Slice<Review> page = reviewRepository.findPageByRestaurantIdOrderByIdDesc(restaurantId, pageable);

        List<Long> memberIds = getMemberIds(page);
        Map<Long, Long> reviewCountByMemberIds = new ReviewCountByMemberIdsDto(
                reviewRepository.findReviewCountByMemberIds(memberIds)
        ).toMap();

        List<ReviewResponse> reviewResponses = page.stream()
                .map(review -> createReviewResponse(githubId, review, reviewCountByMemberIds))
                .collect(Collectors.toList());
        return new ReviewsResponse(page.hasNext(), reviewResponses);
    }

    private List<Long> getMemberIds(final Slice<Review> page) {
        return page.stream()
                .map(Review::getMember)
                .map(Member::getId)
                .collect(Collectors.toList());
    }

    private ReviewResponse createReviewResponse(final String githubId,
                                                final Review review,
                                                final Map<Long, Long> reviewCountByMemberIds) {
        return ReviewResponse.of(review, review.isWriter(githubId), reviewCountByMemberIds.getOrDefault(review.getMember().getId(), ZERO_REVIEW));
    }

    @Transactional
    public void updateReview(final String githubId,
                             final Long reviewId,
                             final ReviewUpdateRequest reviewUpdateRequest) {
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        review.update(member.getGithubId(),
                reviewUpdateRequest.getContent(),
                reviewUpdateRequest.getRating(),
                reviewUpdateRequest.getMenu());
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(final String githubId, final Long reviewId) {
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        if (!review.isWriter(member.getGithubId())) {
            throw new ForbiddenException("리뷰를 삭제 할 권한이 없습니다.");
        }
        reviewRepository.delete(review);
    }
}

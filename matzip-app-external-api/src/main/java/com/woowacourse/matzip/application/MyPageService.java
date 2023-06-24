package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.MyReviewResponse;
import com.woowacourse.matzip.application.response.MyReviewsResponse;
import com.woowacourse.matzip.application.response.ProfileResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.Review;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.RestaurantNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MyPageService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    public MyPageService(final ReviewRepository reviewRepository,
                         final RestaurantRepository restaurantRepository,
                         final MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.memberRepository = memberRepository;
    }

    public ProfileResponse findProfile(final String githubId) {
        Member currentMember = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);

        // TODO: 2023/06/15 reviewRepository 리뷰 개수, 평균 별점 가져오기
        int reviewCount = reviewRepository.findMemberReviewInfosByMemberIds(List.of(currentMember.getId())).size();
        Double averageRating = 0.0;

        return ProfileResponse.of(currentMember, reviewCount, averageRating);
    }

    public MyReviewsResponse findPageByMember(final String githubId, final Pageable pageable) {
        Member currentMember = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);

        Slice<Review> page = reviewRepository.findPageByMemberOrderByIdDesc(currentMember, pageable);

        List<MyReviewResponse> myReviewResponses = page.getContent().stream()
                .map(this::createMyReviewResponse)
                .collect(Collectors.toList());

        return new MyReviewsResponse(page.hasNext(), myReviewResponses);
    }

    private MyReviewResponse createMyReviewResponse(final Review review) {
        Restaurant restaurant = restaurantRepository.findById(review.getRestaurantId())
                .orElseThrow(RestaurantNotFoundException::new);

        return MyReviewResponse.of(review, restaurant);
    }
}

package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.RestaurantResponse;
import com.woowacourse.matzip.application.response.RestaurantTitleResponse;
import com.woowacourse.matzip.application.response.RestaurantTitlesResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.restaurant.SortCondition;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.RestaurantNotFoundException;
import com.woowacourse.matzip.infrastructure.restaurant.RestaurantFindQueryFactory;
import com.woowacourse.matzip.infrastructure.restaurant.RestaurantQueryRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             RestaurantQueryRepository restaurantQueryRepository,
                             ReviewRepository reviewRepository, MemberRepository memberRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantQueryRepository = restaurantQueryRepository;
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
    }

    public RestaurantTitlesResponse findByCampusIdAndCategoryId(final String githubId, final String sortCondition,
                                                                final Long campusId, final Long categoryId,
                                                                final Pageable pageable) {
        String restaurantFindQuery = RestaurantFindQueryFactory.from(sortCondition);
        Slice<Restaurant> restaurants = restaurantQueryRepository.findPageByCampusIdAndCategoryId(restaurantFindQuery,
                campusId, categoryId, pageable);
        return toRestaurantTitlesResponse(githubId, restaurants);
    }

    private RestaurantTitlesResponse toRestaurantTitlesResponse(final String githubId, Slice<Restaurant> page) {
        List<RestaurantTitleResponse> restaurantTitleResponses = page.stream()
                .map(restaurant -> toResponseTitleResponse(githubId, restaurant))
                .collect(Collectors.toList());
        return new RestaurantTitlesResponse(page.hasNext(), restaurantTitleResponses);
    }

    private RestaurantTitleResponse toResponseTitleResponse(final String githubId, final Restaurant restaurant) {
        double rating = reviewRepository.findAverageRatingUsingRestaurantId(restaurant.getId())
                .orElse(0.0);
        return RestaurantTitleResponse.of(restaurant, rating, isBookmarked(githubId, restaurant.getId()));
    }

    public List<RestaurantTitleResponse> findRandomsByCampusId(final String githubId, final Long campusId,
                                                               final int size) {
        return restaurantRepository.findRandomsByCampusId(campusId, size)
                .stream()
                .map(restaurant -> toResponseTitleResponse(githubId, restaurant))
                .collect(Collectors.toList());
    }

    public RestaurantResponse findById(final String githubId, final Long restaurantId) {
        return RestaurantResponse.of(
                restaurantRepository.findById(restaurantId)
                        .orElseThrow(RestaurantNotFoundException::new),
                reviewRepository.findAverageRatingUsingRestaurantId(restaurantId)
                        .orElse(0.0),
                isBookmarked(githubId, restaurantId));
    }

    private boolean isBookmarked(final String githubId, final Long restaurantId) {
        if (Objects.isNull(githubId)) {
            return false;
        }
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        return member.hasBookmarkOf(restaurantId);
    }

    public RestaurantTitlesResponse findTitlesByCampusIdAndNameContainingIgnoreCaseIdDescSort(final String githubId,
                                                                                              final Long campusId,
                                                                                              final String name,
                                                                                              final Pageable pageable) {
        Pageable pageableById = toIdDescSortPageable(pageable);
        return toRestaurantTitlesResponse(
                githubId,
                restaurantRepository.findPageByCampusIdAndNameContainingIgnoreCase(campusId, name, pageableById)
        );
    }

    private Pageable toIdDescSortPageable(final Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), SortCondition.DEFAULT.getValue());
    }

    @Transactional
    public void updateWhenReviewCreate(final Long id, final int rating) {
        restaurantRepository.updateRestaurantByReviewInsert(id, rating);
    }

    @Transactional
    public void updateWhenReviewDelete(final Long id, final int rating) {
        restaurantRepository.updateRestaurantByReviewDelete(id, rating);
    }

    @Transactional
    public void updateWhenReviewUpdate(final Long id, final int ratingGap) {
        restaurantRepository.updateRestaurantByReviewUpdate(id, ratingGap);
    }
}

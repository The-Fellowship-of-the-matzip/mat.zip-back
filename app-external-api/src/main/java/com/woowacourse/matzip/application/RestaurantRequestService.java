package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequestRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.presentation.request.RestaurantRequestCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RestaurantRequestService {

    private final RestaurantRequestRepository restaurantRequestRepository;
    private final MemberRepository memberRepository;

    public RestaurantRequestService(final RestaurantRequestRepository restaurantRequestRepository,
                                    final MemberRepository memberRepository) {
        this.restaurantRequestRepository = restaurantRequestRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createRequest(final String githubId, final Long campusId,
                              final RestaurantRequestCreateRequest request) {
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        RestaurantRequest restaurantRequest = request.toRestaurantRequestWithMemberAndCampusId(member, campusId);
        restaurantRequestRepository.save(restaurantRequest);
    }
}

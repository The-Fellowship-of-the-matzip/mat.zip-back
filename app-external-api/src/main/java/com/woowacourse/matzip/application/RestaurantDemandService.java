package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.RestaurantDemandsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequestRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.RestaurantRequestNotFoundException;
import com.woowacourse.matzip.presentation.request.RestaurantDemandCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantDemandUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RestaurantDemandService {

    private final RestaurantRequestRepository restaurantRequestRepository;
    private final MemberRepository memberRepository;

    public RestaurantDemandService(final RestaurantRequestRepository restaurantRequestRepository,
                                   final MemberRepository memberRepository) {
        this.restaurantRequestRepository = restaurantRequestRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createRequest(final String githubId, final Long campusId,
                              final RestaurantDemandCreateRequest createRequest) {
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        RestaurantDemand restaurantDemand = createRequest.toRestaurantRequestWithMemberAndCampusId(member, campusId);
        restaurantRequestRepository.save(restaurantDemand);
    }

    public RestaurantDemandsResponse findPage(final String githubId, final Long campusId, final Pageable pageable) {
        Slice<RestaurantDemand> page = restaurantRequestRepository.findPageByCampusIdOrderByCreatedAtDesc(campusId,
                pageable);
        return RestaurantDemandsResponse.of(page, githubId);
    }

    @Transactional
    public void updateRequest(final String githubId, final Long requestId,
                              final RestaurantDemandUpdateRequest updateRequest) {
        RestaurantDemand target = restaurantRequestRepository.findById(requestId)
                .orElseThrow(RestaurantRequestNotFoundException::new);

        target.update(updateRequest.toRestaurantRequest(), githubId);
    }

    @Transactional
    public void deleteRequest(final String githubId, final Long requestId) {
        RestaurantDemand target = restaurantRequestRepository.findById(requestId)
                .orElseThrow(RestaurantRequestNotFoundException::new);
        target.validateWriter(githubId);

        restaurantRequestRepository.delete(target);
    }
}

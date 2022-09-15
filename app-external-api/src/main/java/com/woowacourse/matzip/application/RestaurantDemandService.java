package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.RestaurantDemandsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import com.woowacourse.matzip.domain.restaurant.RestaurantDemandRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.RestaurantDemandNotFoundException;
import com.woowacourse.matzip.presentation.request.RestaurantDemandCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantDemandUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RestaurantDemandService {

    private final RestaurantDemandRepository restaurantDemandRepository;
    private final MemberRepository memberRepository;

    public RestaurantDemandService(final RestaurantDemandRepository restaurantDemandRepository,
                                   final MemberRepository memberRepository) {
        this.restaurantDemandRepository = restaurantDemandRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createDemand(final String githubId, final Long campusId,
                             final RestaurantDemandCreateRequest createRequest) {
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        RestaurantDemand restaurantDemand = createRequest.toRestaurantRequestWithMemberAndCampusId(member, campusId);
        restaurantDemandRepository.save(restaurantDemand);
    }

    public RestaurantDemandsResponse findPage(final String githubId, final Long campusId, final Pageable pageable) {
        Slice<RestaurantDemand> page = restaurantDemandRepository.findPageByCampusIdOrderByCreatedAtDesc(campusId,
                pageable);
        return RestaurantDemandsResponse.of(page, githubId);
    }

    @Transactional
    public void updateDemand(final String githubId, final Long requestId,
                             final RestaurantDemandUpdateRequest updateRequest) {
        RestaurantDemand target = restaurantDemandRepository.findById(requestId)
                .orElseThrow(RestaurantDemandNotFoundException::new);

        target.update(updateRequest.toRestaurantRequest(), githubId);
    }

    @Transactional
    public void deleteDemand(final String githubId, final Long requestId) {
        RestaurantDemand target = restaurantDemandRepository.findById(requestId)
                .orElseThrow(RestaurantDemandNotFoundException::new);
        target.validateWriter(githubId);
        target.validateNotRegistered();

        restaurantDemandRepository.delete(target);
    }
}

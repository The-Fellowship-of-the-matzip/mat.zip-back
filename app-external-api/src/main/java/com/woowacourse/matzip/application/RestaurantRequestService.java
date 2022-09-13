package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.RestaurantRequestsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequestRepository;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.RestaurantRequestNotFoundException;
import com.woowacourse.matzip.presentation.request.RestaurantRequestCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantRequestUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
                              final RestaurantRequestCreateRequest createRequest) {
        Member member = memberRepository.findMemberByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        RestaurantRequest restaurantRequest = createRequest.toRestaurantRequestWithMemberAndCampusId(member, campusId);
        restaurantRequestRepository.save(restaurantRequest);
    }

    public RestaurantRequestsResponse findPage(final String githubId, final Long campusId, final Pageable pageable) {
        Slice<RestaurantRequest> page = restaurantRequestRepository.findPageByCampusIdOrderByCreatedAtDesc(campusId,
                pageable);
        return RestaurantRequestsResponse.of(page, githubId);
    }

    @Transactional
    public void updateRequest(final String githubId, final Long requestId,
                              final RestaurantRequestUpdateRequest updateRequest) {
        RestaurantRequest target = restaurantRequestRepository.findById(requestId)
                .orElseThrow(RestaurantRequestNotFoundException::new);

        target.update(updateRequest.toRestaurantRequest(), githubId);
    }

    @Transactional
    public void deleteRequest(final String githubId, final Long requestId) {
        RestaurantRequest target = restaurantRequestRepository.findById(requestId)
                .orElseThrow(RestaurantRequestNotFoundException::new);

        validateWriter(githubId, target);
        restaurantRequestRepository.delete(target);
    }

    private void validateWriter(final String githubId, final RestaurantRequest target) {
        if (!target.isWriter(githubId)) {
            throw new ForbiddenException("식당 추가 요청을 삭제할 권한이 없습니다.");
        }
    }
}

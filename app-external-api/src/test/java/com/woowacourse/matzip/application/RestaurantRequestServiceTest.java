package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.ORI;
import static com.woowacourse.matzip.support.RestaurantRequestFixtures.RESTAURANT_REQUEST_1;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestRestaurantRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.application.response.RestaurantRequestsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequestRepository;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.exception.RestaurantRequestNotFoundException;
import com.woowacourse.matzip.presentation.request.RestaurantRequestCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantRequestUpdateRequest;
import com.woowacourse.support.SpringServiceTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@SpringServiceTest
class RestaurantRequestServiceTest {

    @Autowired
    private RestaurantRequestService restaurantRequestService;

    @Autowired
    private RestaurantRequestRepository restaurantRequestRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static RestaurantRequestCreateRequest restaurantRequestCreateRequest() {
        return new RestaurantRequestCreateRequest(RESTAURANT_REQUEST_1.getCategoryId(), RESTAURANT_REQUEST_1.getName());
    }

    @Test
    void 없는_유저가_요청할_경우_예외_발생() {
        assertThatThrownBy(
                () -> restaurantRequestService.createRequest("nonUser", 1L, restaurantRequestCreateRequest()));
    }

    @Test
    void 식당_추가_요청을_생성한다() {
        Member member = memberRepository.save(ORI.toMember());

        assertDoesNotThrow(() -> restaurantRequestService.createRequest(member.getGithubId(), 1L,
                restaurantRequestCreateRequest()));
    }

    @Test
    void 캠퍼스_별_식당_추가_요청을_조회한다() {
        Member member = memberRepository.save(ORI.toMember());
        restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당", member));

        RestaurantRequestsResponse response = restaurantRequestService.findPage(member.getGithubId(), 1L,
                PageRequest.of(0, 1));

        assertAll(() -> assertThat(response.getItems()).hasSize(1)
                        .extracting("updatable")
                        .containsExactly(true), // 변경 가능한지(작성자인지) 확인
                () -> assertThat(response.isHasNext()).isFalse());
    }

    @Test
    void 식당_추가_요청을_수정한다() {
        Member member = memberRepository.save(ORI.toMember());
        Long requestId = restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당", member))
                .getId();
        RestaurantRequestUpdateRequest updateRequest = new RestaurantRequestUpdateRequest(2L, "수정식당");

        restaurantRequestService.updateRequest(member.getGithubId(), requestId, updateRequest);

        RestaurantRequest actual = restaurantRequestRepository.findById(requestId)
                .get();
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "campusId", "member", "createdAt")
                .isEqualTo(updateRequest.toRestaurantRequest());
    }

    @Test
    void 존재하지_않는_식당_추가_요청을_수정할_수_없다() {
        assertThatThrownBy(() -> restaurantRequestService.updateRequest(null, 1L, null))
                .isExactlyInstanceOf(RestaurantRequestNotFoundException.class);
    }

    @Test
    void 작성자가_아니면_식당_추가_요청을_수정할_수_없다() {
        Member member = memberRepository.save(ORI.toMember());
        Long requestId = restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당", member))
                .getId();
        RestaurantRequestUpdateRequest updateRequest = new RestaurantRequestUpdateRequest(2L, "수정식당");

        assertThatThrownBy(() -> restaurantRequestService.updateRequest(null, requestId, updateRequest))
                .isExactlyInstanceOf(ForbiddenException.class)
                .hasMessage("식당 추가 요청을 수정할 권한이 없습니다.");
    }

    @Test
    void 식당_추가_요청을_삭제한다() {
        Member member = memberRepository.save(ORI.toMember());
        Long requestId = restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당", member))
                .getId();

        restaurantRequestService.deleteRequest(member.getGithubId(), requestId);

        Optional<RestaurantRequest> actual = restaurantRequestRepository.findById(requestId);
        assertThat(actual).isEmpty();
    }

    @Test
    void 존재하지_않는_식당_추가_요청을_삭제할_수_없다() {
        assertThatThrownBy(() -> restaurantRequestService.deleteRequest(null, 1L))
                .isExactlyInstanceOf(RestaurantRequestNotFoundException.class);
    }

    @Test
    void 작성자가_아니면_식당_추가_요청을_삭제할_수_없다() {
        Member member = memberRepository.save(ORI.toMember());
        Long requestId = restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당", member))
                .getId();

        assertThatThrownBy(() -> restaurantRequestService.deleteRequest(null, requestId))
                .isExactlyInstanceOf(ForbiddenException.class)
                .hasMessage("식당 추가 요청을 삭제할 권한이 없습니다.");
    }
}

package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.ORI;
import static com.woowacourse.matzip.support.RestaurantRequestFixtures.RESTAURANT_REQUEST_1;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestRestaurantRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.application.response.RestaurantDemandsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequestRepository;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.exception.RestaurantRequestNotFoundException;
import com.woowacourse.matzip.presentation.request.RestaurantDemandCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantDemandUpdateRequest;
import com.woowacourse.support.SpringServiceTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@SpringServiceTest
class RestaurantDemandServiceTest {

    @Autowired
    private RestaurantDemandService restaurantDemandService;

    @Autowired
    private RestaurantRequestRepository restaurantRequestRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static RestaurantDemandCreateRequest restaurantRequestCreateRequest() {
        return new RestaurantDemandCreateRequest(RESTAURANT_REQUEST_1.getCategoryId(), RESTAURANT_REQUEST_1.getName());
    }

    @Test
    void 없는_유저가_식당_추가를_요청할_경우_예외_발생() {
        assertThatThrownBy(
                () -> restaurantDemandService.createRequest("nonUser", 1L, restaurantRequestCreateRequest()));
    }

    @Test
    void 식당_추가_요청을_생성한다() {
        Member member = memberRepository.save(ORI.toMember());

        assertDoesNotThrow(() -> restaurantDemandService.createRequest(member.getGithubId(), 1L,
                restaurantRequestCreateRequest()));
    }

    @Test
    void 캠퍼스_별_식당_추가_요청을_최신순으로_조회한다() {
        Member member = memberRepository.save(ORI.toMember());
        restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당1", member));
        restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당2", member));

        RestaurantDemandsResponse response = restaurantDemandService.findPage(member.getGithubId(), 1L,
                PageRequest.of(0, 1));

        assertAll(() -> assertThat(response.getItems()).hasSize(1)
                        .extracting("updatable")
                        .containsExactly(true), // 변경 가능한지(작성자인지) 확인
                () -> assertThat(response.getItems())
                        .extracting("name")
                        .containsExactly("식당2"),
                () -> assertThat(response.isHasNext()).isTrue());
    }

    @Test
    void 식당_추가_요청을_수정한다() {
        Member member = memberRepository.save(ORI.toMember());
        Long requestId = restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당", member))
                .getId();
        RestaurantDemandUpdateRequest updateRequest = new RestaurantDemandUpdateRequest(2L, "수정식당");

        restaurantDemandService.updateRequest(member.getGithubId(), requestId, updateRequest);

        RestaurantDemand actual = restaurantRequestRepository.findById(requestId)
                .get();
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "campusId", "member", "createdAt")
                .isEqualTo(updateRequest.toRestaurantRequest());
    }

    @Test
    void 존재하지_않는_식당_추가_요청을_수정할_수_없다() {
        assertThatThrownBy(() -> restaurantDemandService.updateRequest(null, 1L, null))
                .isExactlyInstanceOf(RestaurantRequestNotFoundException.class);
    }

    @Test
    void 작성자가_아니면_식당_추가_요청을_수정할_수_없다() {
        Member member = memberRepository.save(ORI.toMember());
        Long requestId = restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당", member))
                .getId();
        RestaurantDemandUpdateRequest updateRequest = new RestaurantDemandUpdateRequest(2L, "수정식당");

        assertThatThrownBy(() -> restaurantDemandService.updateRequest(null, requestId, updateRequest))
                .isExactlyInstanceOf(ForbiddenException.class)
                .hasMessage("식당 추가 요청에 대한 권한이 없습니다.");
    }

    @Test
    void 식당_추가_요청을_삭제한다() {
        Member member = memberRepository.save(ORI.toMember());
        Long requestId = restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당", member))
                .getId();

        restaurantDemandService.deleteRequest(member.getGithubId(), requestId);

        Optional<RestaurantDemand> actual = restaurantRequestRepository.findById(requestId);
        assertThat(actual).isEmpty();
    }

    @Test
    void 존재하지_않는_식당_추가_요청을_삭제할_수_없다() {
        assertThatThrownBy(() -> restaurantDemandService.deleteRequest(null, 1L))
                .isExactlyInstanceOf(RestaurantRequestNotFoundException.class);
    }

    @Test
    void 작성자가_아니면_식당_추가_요청을_삭제할_수_없다() {
        Member member = memberRepository.save(ORI.toMember());
        Long requestId = restaurantRequestRepository.save(createTestRestaurantRequest(1L, 1L, "식당", member))
                .getId();

        assertThatThrownBy(() -> restaurantDemandService.deleteRequest(null, requestId))
                .isExactlyInstanceOf(ForbiddenException.class)
                .hasMessage("식당 추가 요청에 대한 권한이 없습니다.");
    }
}

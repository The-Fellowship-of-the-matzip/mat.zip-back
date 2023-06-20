package com.woowacourse.matzip.application;

import static com.woowacourse.matzip.MemberFixtures.ORI;
import static com.woowacourse.matzip.RestaurantRequestFixtures.RESTAURANT_REQUEST_1;
import static com.woowacourse.matzip.TestFixtureCreateUtil.createTestRestaurantDemand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.application.response.RestaurantDemandsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.RestaurantDemand;
import com.woowacourse.matzip.domain.restaurant.RestaurantDemandRepository;
import com.woowacourse.matzip.exception.AlreadyRegisteredException;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.exception.RestaurantDemandNotFoundException;
import com.woowacourse.matzip.presentation.request.RestaurantDemandCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantDemandUpdateRequest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class RestaurantDemandServiceTest extends ServiceTest {

    @Autowired
    private RestaurantDemandService restaurantDemandService;

    @Autowired
    private RestaurantDemandRepository restaurantDemandRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static RestaurantDemandCreateRequest restaurantDemandCreateRequest() {
        return new RestaurantDemandCreateRequest(RESTAURANT_REQUEST_1.getCategoryId(), RESTAURANT_REQUEST_1.getName());
    }

    @Test
    void 없는_유저가_식당_추가를_요청할_경우_예외_발생() {
        assertThatThrownBy(
                () -> restaurantDemandService.createDemand("nonUser", 1L, restaurantDemandCreateRequest()));
    }

    @Test
    void 식당_추가_요청을_생성한다() {
        Member member = memberRepository.save(ORI.toMember());

        assertDoesNotThrow(() -> restaurantDemandService.createDemand(member.getGithubId(), 1L,
                restaurantDemandCreateRequest()));
    }

    @Test
    void 캠퍼스_별_식당_추가_요청을_최신순으로_조회한다() {
        Member member = memberRepository.save(ORI.toMember());
        restaurantDemandRepository.save(createTestRestaurantDemand(1L, 1L, "식당1", member));
        restaurantDemandRepository.save(createTestRestaurantDemand(1L, 1L, "식당2", member));

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
        Long demandId = restaurantDemandRepository.save(createTestRestaurantDemand(1L, 1L, "식당", member))
                .getId();
        RestaurantDemandUpdateRequest updateRequest = new RestaurantDemandUpdateRequest(2L, "수정식당");

        restaurantDemandService.updateDemand(member.getGithubId(), demandId, updateRequest);

        RestaurantDemand actual = restaurantDemandRepository.findById(demandId)
                .get();
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "campusId", "member", "createdAt")
                .isEqualTo(updateRequest.toRestaurantRequest());
    }

    @Test
    void 존재하지_않는_식당_추가_요청을_수정할_수_없다() {
        assertThatThrownBy(() -> restaurantDemandService.updateDemand(null, 1L, null))
                .isExactlyInstanceOf(RestaurantDemandNotFoundException.class);
    }

    @Test
    void 작성자가_아니면_식당_추가_요청을_수정할_수_없다() {
        Member member = memberRepository.save(ORI.toMember());
        Long demandId = restaurantDemandRepository.save(createTestRestaurantDemand(1L, 1L, "식당", member))
                .getId();
        RestaurantDemandUpdateRequest updateRequest = new RestaurantDemandUpdateRequest(2L, "수정식당");

        assertThatThrownBy(() -> restaurantDemandService.updateDemand(null, demandId, updateRequest))
                .isExactlyInstanceOf(ForbiddenException.class)
                .hasMessage("식당 추가 요청에 대한 권한이 없습니다.");
    }

    @Test
    void 이미_처리된_식당_추가_요청은_수정할_수_없다() {
        Member member = memberRepository.save(ORI.toMember());
        RestaurantDemand restaurantDemand = this.restaurantDemandRepository.save(
                createTestRestaurantDemand(1L, 1L, "식당", member));
        restaurantDemand.register();
        RestaurantDemandUpdateRequest updateRequest = new RestaurantDemandUpdateRequest(2L, "수정식당");

        assertThatThrownBy(() -> restaurantDemandService.updateDemand(member.getGithubId(), restaurantDemand.getId(),
                updateRequest)).isExactlyInstanceOf(AlreadyRegisteredException.class);
    }

    @Test
    void 식당_추가_요청을_삭제한다() {
        Member member = memberRepository.save(ORI.toMember());
        Long demandId = restaurantDemandRepository.save(createTestRestaurantDemand(1L, 1L, "식당", member))
                .getId();

        restaurantDemandService.deleteDemand(member.getGithubId(), demandId);

        Optional<RestaurantDemand> actual = restaurantDemandRepository.findById(demandId);
        assertThat(actual).isEmpty();
    }

    @Test
    void 존재하지_않는_식당_추가_요청을_삭제할_수_없다() {
        assertThatThrownBy(() -> restaurantDemandService.deleteDemand(null, 1L))
                .isExactlyInstanceOf(RestaurantDemandNotFoundException.class);
    }

    @Test
    void 작성자가_아니면_식당_추가_요청을_삭제할_수_없다() {
        Member member = memberRepository.save(ORI.toMember());
        Long demandId = restaurantDemandRepository.save(createTestRestaurantDemand(1L, 1L, "식당", member))
                .getId();

        assertThatThrownBy(() -> restaurantDemandService.deleteDemand(null, demandId))
                .isExactlyInstanceOf(ForbiddenException.class)
                .hasMessage("식당 추가 요청에 대한 권한이 없습니다.");
    }

    @Test
    void 이미_처리된_식당_추가_요청은_삭제할_수_없다() {
        Member member = memberRepository.save(ORI.toMember());
        RestaurantDemand restaurantDemand = this.restaurantDemandRepository.save(
                createTestRestaurantDemand(1L, 1L, "식당", member));
        restaurantDemand.register();

        assertThatThrownBy(() -> restaurantDemandService.deleteDemand(member.getGithubId(), restaurantDemand.getId()))
                .isExactlyInstanceOf(AlreadyRegisteredException.class);
    }
}

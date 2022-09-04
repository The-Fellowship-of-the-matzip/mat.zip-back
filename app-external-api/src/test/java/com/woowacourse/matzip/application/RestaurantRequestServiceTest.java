package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.ORI;
import static com.woowacourse.matzip.support.RestaurantRequestFixtures.RESTAURANT_REQUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.application.response.RestaurantRequestsResponse;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.presentation.request.RestaurantRequestCreateRequest;
import com.woowacourse.support.SpringServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@SpringServiceTest
class RestaurantRequestServiceTest {

    @Autowired
    private RestaurantRequestService restaurantRequestService;

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
        식당_추가_요청을_생성한다();

        RestaurantRequestsResponse response = restaurantRequestService.findPage(ORI.getGithubId(), 1L,
                PageRequest.of(0, 1));

        assertAll(
                () -> assertThat(response.getItems()).hasSize(1)
                        .extracting("updatable")
                        .containsExactly(true), // 변경 가능한지(작성자인지) 확인
                () -> assertThat(response.isHasNext()).isTrue()
        );
    }
}

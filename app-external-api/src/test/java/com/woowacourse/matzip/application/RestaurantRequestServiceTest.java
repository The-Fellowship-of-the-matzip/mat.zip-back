package com.woowacourse.matzip.application;

import static com.woowacourse.auth.support.GithubResponseFixtures.ORI;
import static com.woowacourse.matzip.support.RestaurantRequestFixtures.RESTAURANT_REQUEST_1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.presentation.request.RestaurantRequestCreateRequest;
import com.woowacourse.support.SpringServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}

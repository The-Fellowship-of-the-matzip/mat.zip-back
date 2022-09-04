package com.woowacourse.matzip.domain.restaurant;

import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestMember;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestRestaurantRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.support.config.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
@Import(JpaConfig.class)
class RestaurantRequestRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RestaurantRequestRepository restaurantRequestRepository;

    @Test
    void 캠퍼스별_식당_추가_요청_목록을_최신순으로_조회한다() {
        Member member = createTestMember();
        memberRepository.save(member);
        RestaurantRequest restaurantRequest1 = createTestRestaurantRequest(1L, 1L, "식당1", member);
        RestaurantRequest restaurantRequest2 = createTestRestaurantRequest(1L, 1L, "식당2", member);
        restaurantRequestRepository.save(restaurantRequest1);
        restaurantRequestRepository.save(restaurantRequest2);

        Slice<RestaurantRequest> slice = restaurantRequestRepository.findPageByCampusIdOrderByCreatedAtDesc(1L,
                PageRequest.of(0, 1));

        assertAll(
                () -> assertThat(slice.hasNext()).isTrue(),
                () -> assertThat(slice.getContent()).hasSize(1)
                        .containsExactly(restaurantRequest2)
        );
    }
}

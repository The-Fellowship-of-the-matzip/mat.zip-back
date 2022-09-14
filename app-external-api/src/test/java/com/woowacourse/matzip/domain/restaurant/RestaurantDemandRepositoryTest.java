package com.woowacourse.matzip.domain.restaurant;

import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestMember;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestRestaurantDemand;
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
class RestaurantDemandRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RestaurantDemandRepository restaurantDemandRepository;

    @Test
    void 캠퍼스별_식당_추가_요청_목록을_최신순으로_조회한다() {
        Member member = createTestMember();
        memberRepository.save(member);
        RestaurantDemand restaurantDemand1 = createTestRestaurantDemand(1L, 1L, "식당1", member);
        RestaurantDemand restaurantDemand2 = createTestRestaurantDemand(1L, 1L, "식당2", member);
        restaurantDemandRepository.save(restaurantDemand1);
        restaurantDemandRepository.save(restaurantDemand2);

        Slice<RestaurantDemand> slice = restaurantDemandRepository.findPageByCampusIdOrderByCreatedAtDesc(1L,
                PageRequest.of(0, 1));

        assertAll(
                () -> assertThat(slice.hasNext()).isTrue(),
                () -> assertThat(slice.getContent()).hasSize(1)
                        .containsExactly(restaurantDemand2)
        );
    }
}

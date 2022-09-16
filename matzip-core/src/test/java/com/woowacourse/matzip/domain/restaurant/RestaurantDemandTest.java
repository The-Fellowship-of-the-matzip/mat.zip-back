package com.woowacourse.matzip.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.exception.AlreadyRegisteredException;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.exception.InvalidLengthException;
import org.junit.jupiter.api.Test;

class RestaurantDemandTest {

    @Test
    void 식당_추가_요청_생성_시_이름_길이_제한() {
        String name = "식당의 이름이 이렇게 길 수 없습니다.";

        assertThatThrownBy(() -> RestaurantDemand.builder()
                .name(name)
                .build())
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage("식당 이름은(는) 길이가 20 이하의 값만 입력할 수 있습니다.");
    }

    @Test
    void 식당_추가_요청을_수정한다() {
        Member member = Member.builder()
                .githubId("githubId")
                .build();

        RestaurantDemand target = RestaurantDemand.builder()
                .id(1L)
                .categoryId(1L)
                .campusId(1L)
                .name("식당")
                .member(member)
                .build();

        RestaurantDemand updateRequest = RestaurantDemand.builder()
                .categoryId(2L)
                .name("변경된 식당 이름")
                .build();

        target.update(updateRequest, "githubId");

        assertAll(
                () -> assertThat(target.getId()).isEqualTo(1L),
                () -> assertThat(target).usingRecursiveComparison()
                        .ignoringFields("id", "campusId", "member")
                        .isEqualTo(updateRequest)
        );
    }

    @Test
    void 식당_추가_요청_수정은_id를_바꾸지_않는다() {
        Member member = Member.builder()
                .githubId("githubId")
                .build();

        RestaurantDemand target = RestaurantDemand.builder()
                .id(1L)
                .name("식당")
                .member(member)
                .build();

        RestaurantDemand updateRequest = RestaurantDemand.builder()
                .id(2L)
                .name("식당")
                .build();

        target.update(updateRequest, "githubId");

        assertThat(target.getId()).isOne();
    }

    @Test
    void 작성자가_아니면_식당_추가_요청을_수정할_수_없다() {
        Member member = Member.builder()
                .githubId("githubId")
                .build();

        RestaurantDemand target = RestaurantDemand.builder()
                .id(1L)
                .name("식당")
                .member(member)
                .build();

        RestaurantDemand updateRequest = RestaurantDemand.builder()
                .id(2L)
                .name("식당")
                .build();

        assertThatThrownBy(() -> target.update(updateRequest, "wrongGithubId"))
                .isExactlyInstanceOf(ForbiddenException.class)
                .hasMessage("식당 추가 요청에 대한 권한이 없습니다.");
    }

    @Test
    void 등록된_식당_추가_요청은_수정할_수_없다() {
        Member member = Member.builder()
                .githubId("githubId")
                .build();

        RestaurantDemand target = RestaurantDemand.builder()
                .id(1L)
                .name("식당")
                .member(member)
                .registered(true)
                .build();

        RestaurantDemand updateRequest = RestaurantDemand.builder()
                .id(2L)
                .name("식당")
                .build();

        assertThatThrownBy(() -> target.update(updateRequest, "githubId"))
                .isExactlyInstanceOf(AlreadyRegisteredException.class);
    }

    @Test
    void 식당_추가_요청을_등록_상태로_바꾼다() {
        RestaurantDemand target = RestaurantDemand.builder()
                .name("식당")
                .build();

        target.register();

        assertThat(target.isRegistered()).isTrue();
    }

    @Test
    void 요청한_식당이_등록되지_않았는지_검증한다() {
        RestaurantDemand target = RestaurantDemand.builder()
                .name("식당")
                .registered(true)
                .build();

        assertThatThrownBy(target::validateNotRegistered).isExactlyInstanceOf(AlreadyRegisteredException.class);
    }

    @Test
    void 이미_등록된_식당_추가_요청은_등록_상태로_바꿀_수_없다() {
        RestaurantDemand target = RestaurantDemand.builder()
                .name("식당")
                .registered(true)
                .build();

        assertThatThrownBy(target::register).isExactlyInstanceOf(AlreadyRegisteredException.class);
    }
}

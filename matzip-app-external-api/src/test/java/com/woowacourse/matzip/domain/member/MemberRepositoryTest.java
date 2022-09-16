package com.woowacourse.matzip.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.support.config.JpaConfig;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 유저_저장_시_생성시간이_추가된다() {
        LocalDateTime currentTime = LocalDateTime.now();
        Member member = memberRepository.save(Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build());

        assertThat(member.getCreatedAt()).isAfter(currentTime);
    }
}

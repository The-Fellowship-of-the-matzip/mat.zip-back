package com.woowacourse.matzip.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.matzip.MemberFixtures;
import com.woowacourse.matzip.RestaurantFixture;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
@EnableJpaAuditing
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

    @Test
    void 북마크를_저장한다() {
        Member huni = MemberFixtures.HUNI.toMember();
        huni.addBookmark(RestaurantFixture.RESTAURANT_1.toRestaurantWithCategoryIdAndCampusId(1L, 1L));
        memberRepository.save(huni);
        Member huniAfterBookmark = memberRepository.findMemberByGithubId(huni.getGithubId()).get();

        assertThat(huniAfterBookmark.getBookmarks()).hasSize(1);
    }

    @Test
    void 북마크를_삭제한다() {
        // 북마크 저장
        Member huni = MemberFixtures.HUNI.toMember();
        Restaurant restaurant = RestaurantFixture.RESTAURANT_1.toRestaurantWithCategoryIdAndCampusId(1L, 1L);
        huni.addBookmark(restaurant);
        memberRepository.save(huni);
        Member huniAfterBookmark = memberRepository.findMemberByGithubId(huni.getGithubId()).get();

        // 북마크 삭제
        huniAfterBookmark.deleteBookmark(restaurant);
        memberRepository.save(huniAfterBookmark);

        // 검증
        Member huniAfterDeleteBookmark = memberRepository.findMemberByGithubId(huni.getGithubId()).get();
        assertThat(huniAfterDeleteBookmark.getBookmarks()).hasSize(0);
    }
}

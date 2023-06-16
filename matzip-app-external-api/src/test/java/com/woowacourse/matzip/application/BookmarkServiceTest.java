package com.woowacourse.matzip.application;

import static com.woowacourse.matzip.MemberFixtures.ORI;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.support.SpringServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringServiceTest
public class BookmarkServiceTest {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 북마크를_저장한다() {
        Member member = memberRepository.save(ORI.toMember());
        Restaurant restaurant = restaurantRepository.findAll().get(0);

        assertDoesNotThrow(
                () -> bookmarkService.createBookmark(member.getGithubId(), restaurant.getId())
        );
    }

    @Test
    void 북마크를_삭제한다() {
        Member member = memberRepository.save(ORI.toMember());
        Restaurant restaurant = restaurantRepository.findAll().get(0);
        bookmarkService.createBookmark(member.getGithubId(), restaurant.getId());

        assertDoesNotThrow(
                () -> bookmarkService.deleteBookmark(member.getGithubId(), restaurant.getId())
        );
    }
}

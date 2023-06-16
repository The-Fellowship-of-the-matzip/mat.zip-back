package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.RestaurantNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookmarkService {

    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    public BookmarkService(MemberRepository memberRepository, RestaurantRepository restaurantRepository) {
        this.memberRepository = memberRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public void createBookmark(final String githubId, final Long restaurantId) {
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        Restaurant restaurantToBookmark = restaurantRepository.findById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);
        member.addBookmark(restaurantToBookmark);
        memberRepository.save(member);
    }

    @Transactional
    public void deleteBookmark(final String githubId, final Long restaurantId) {

    }
}

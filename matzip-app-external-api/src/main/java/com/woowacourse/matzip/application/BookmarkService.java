package com.woowacourse.matzip.application;

import com.woowacourse.matzip.domain.bookmark.Bookmark;
import com.woowacourse.matzip.domain.bookmark.BookmarkRepository;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.exception.AlreadyBookmarkedException;
import com.woowacourse.matzip.exception.MemberNotFoundException;
import com.woowacourse.matzip.exception.RestaurantNotFoundException;
import java.lang.reflect.UndeclaredThrowableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookmarkService {

    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;
    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(MemberRepository memberRepository, RestaurantRepository restaurantRepository,
                           BookmarkRepository bookmarkRepository) {
        this.memberRepository = memberRepository;
        this.restaurantRepository = restaurantRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    @Transactional
    public void createBookmark(final String githubId, final Long restaurantId) {
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);
        Bookmark bookmark = Bookmark.builder()
                .member(member)
                .restaurant(restaurant)
                .build();
        try {
            bookmarkRepository.save(bookmark);
        } catch (UndeclaredThrowableException exception) {
            throw new AlreadyBookmarkedException();
        }
    }

    @Transactional
    public void deleteBookmark(final String githubId, final Long restaurantId) {
        Member member = memberRepository.findMemberByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        bookmarkRepository.deleteBookmarkByMemberIdAndRestaurantId(member.getId(), restaurantId);
    }
}

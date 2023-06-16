package com.woowacourse.matzip.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.RestaurantFixture;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.exception.AlreadyBookmarkedException;
import com.woowacourse.matzip.exception.BookmarkNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class MemberTest {

    @Test
    void 이름을_업데이트한다() {
        Member member = new Member(1L, "1", "huni", "image.png");
        member.updateUsername("huno");
        assertThat(member.getUsername()).isEqualTo("huno");
    }

    @Test
    void 이미지를_업데이트한다() {
        Member member = new Member(1L, "1", "huni", "image.png");
        member.updateProfileImage("update.png");
        assertThat(member.getProfileImage()).isEqualTo("update.png");
    }

    @Test
    void github_아이디가_같다() {
        Member member = new Member(1L, "1", "huni", "image.png");
        assertThat(member.isSameGithubId("1")).isTrue();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "2")
    void github_아이디가_null이다(String githubId) {
        Member member = new Member(1L, "1", "huni", "image.png");
        assertThat(member.isSameGithubId(githubId)).isFalse();
    }


    @Nested
    class 북마크_추가 {

        @Test
        void 북마크를_추가한다() {
            Member member = new Member(1L, "1", "huni", "image.png");
            member.addBookmark(RestaurantFixture.RESTAURANT_1.toRestaurantWithCategoryIdAndCampusId(1L, 1L));
            assertThat(member.getBookmarks()).hasSize(1);
        }

        @Test
        void 동일한_북마크를_추가하면_예외가_발생한다() {
            Member member = new Member(1L, "1", "huni", "image.png");
            Restaurant restaurant = RestaurantFixture.RESTAURANT_1.toRestaurantWithCategoryIdAndCampusId(1L, 1L);
            member.addBookmark(restaurant);

            assertThatThrownBy(() -> member.addBookmark(restaurant))
                    .isInstanceOf(AlreadyBookmarkedException.class);
        }
    }

    @Nested
    class 북마크_삭제 {

        @Test
        void 북마크를_삭제한다() {
            Member member = new Member(1L, "1", "huni", "image.png");
            Restaurant restaurant = RestaurantFixture.RESTAURANT_1.toRestaurantWithCategoryIdAndCampusId(1L, 1L);
            member.addBookmark(restaurant);

            member.deleteBookmark(restaurant);
            assertThat(member.getBookmarks()).hasSize(0);
        }

        @Test
        void 존재하지_않는_북마크를_삭제하면_예외가_발생한다() {
            Member member = new Member(1L, "1", "huni", "image.png");
            Restaurant restaurant = RestaurantFixture.RESTAURANT_1.toRestaurantWithCategoryIdAndCampusId(1L, 1L);

            assertThatThrownBy(() -> member.deleteBookmark(restaurant))
                    .isInstanceOf(BookmarkNotFoundException.class);

        }
    }
}

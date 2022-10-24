package com.woowacourse.matzip.domain.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.exception.ForbiddenException;
import com.woowacourse.matzip.exception.InvalidLengthException;
import com.woowacourse.matzip.exception.InvalidReviewException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ReviewTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    void 별점_범위제한인_경우_예외발생(final int score) {
        assertThatThrownBy(() -> Review.builder().rating(score).build())
                .isInstanceOf(InvalidReviewException.class)
                .hasMessage("리뷰 점수는 1점부터 5점까지만 가능합니다.");
    }

    @Test
    void 리뷰_생성_시_메뉴_이름_길이_제한() {
        String menu = "리뷰의 메뉴 이름이 이렇게 길 수 없습니다.";

        assertThatThrownBy(() -> Review.builder()
                .menu(menu)
                .content("리뷰내용")
                .rating(3)
                .build())
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage("메뉴의 이름은(는) 길이가 20 이하의 값만 입력할 수 있습니다.");
    }

    @Test
    void 리뷰_생성_시_리뷰_내용_길이_제한() {
        String content = IntStream.rangeClosed(1, 256)
                .mapToObj(index -> "a")
                .collect(Collectors.joining());

        assertThatThrownBy(() -> Review.builder()
                .menu("메뉴")
                .content(content)
                .rating(3)
                .build())
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage("리뷰 내용은(는) 길이가 255 이하의 값만 입력할 수 있습니다.");
    }

    @Test
    void 작성자이다() {
        Member member = new Member(1L, "1", "huni", "image.png");
        Review review = new Review(1L, member, 1L, "리뷰 내용", 3, "메뉴", LocalDateTime.now());
        assertThat(review.isWriter("1")).isTrue();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "2")
    void 작성자가_아니다(String githubId) {
        Member member = new Member(1L, "1", "huni", "image.png");
        Review review = new Review(1L, member, 1L, "리뷰 내용", 3, "메뉴", LocalDateTime.now());
        assertThat(review.isWriter(githubId)).isFalse();
    }

    @Test
    void 업데이트_권한이_없어_실패한다() {
        Member huni = new Member(1L, "1", "huni", "image.png");
        Member ori = new Member(2L, "2", "ori", "image.png");
        Review review = new Review(1L, huni, 1L, "리뷰 내용", 3, "메뉴", LocalDateTime.now());

        assertThatThrownBy(
                () -> review.update(ori.getGithubId(), "리뷰 내용 2", 4, "메뉴 2")
        )
                .isInstanceOf(ForbiddenException.class)
                .hasMessage("리뷰를 업데이트 할 권한이 없습니다.");
    }

    @Test
    void 업데이트시_리뷰_내용_길이제한() {
        String content = IntStream.rangeClosed(1, 256)
                .mapToObj(index -> "a")
                .collect(Collectors.joining());
        Member huni = new Member(1L, "1", "huni", "image.png");
        Review review = new Review(1L, huni, 1L, "리뷰 내용", 3, "메뉴", LocalDateTime.now());

        assertThatThrownBy(
                () -> review.update(huni.getGithubId(), content, 4, "메뉴")
        )
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage("리뷰 내용은(는) 길이가 255 이하의 값만 입력할 수 있습니다.");
    }

    @Test
    void 업데이트시_리뷰_메뉴_길이제한() {
        String menu = "리뷰의 메뉴 이름이 이렇게 길 수 없습니다.";
        Member huni = new Member(1L, "1", "huni", "image.png");
        Review review = new Review(1L, huni, 1L, "리뷰 내용", 3, "메뉴", LocalDateTime.now());

        assertThatThrownBy(
                () -> review.update(huni.getGithubId(), "리뷰 내용", 4, menu)
        )
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage("메뉴의 이름은(는) 길이가 20 이하의 값만 입력할 수 있습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    void 업데이트시_별점_범위제한인_경우_예외발생(final int score) {
        Member huni = new Member(1L, "1", "huni", "image.png");
        Review review = new Review(1L, huni, 1L, "리뷰 내용", 3, "메뉴", LocalDateTime.now());

        assertThatThrownBy(() -> review.update(huni.getGithubId(), "리뷰 내용", score, "메뉴 2"))
                .isInstanceOf(InvalidReviewException.class)
                .hasMessage("리뷰 점수는 1점부터 5점까지만 가능합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"4,-1", "3,0", "2,1"})
    void 리뷰_점수_차를_반환한다(final int rating, final long expected) {
        Member huni = new Member(1L, "1", "huni", "image.png");
        Review review = new Review(1L, huni, 1L, "리뷰 내용", 3, "메뉴", LocalDateTime.now());

        long actual = review.reviewGap(rating);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    void 입력된_리뷰점수가_범위제한인_경우_예외발생(final int rating) {
        Member huni = new Member(1L, "1", "huni", "image.png");
        Review review = new Review(1L, huni, 1L, "리뷰 내용", 3, "메뉴", LocalDateTime.now());

        assertThatThrownBy(() -> review.reviewGap(rating))
                .isInstanceOf(InvalidReviewException.class)
                .hasMessage("리뷰 점수는 1점부터 5점까지만 가능합니다.");
    }
}

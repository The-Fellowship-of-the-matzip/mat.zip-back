package com.woowacourse.matzip.infrastructure.restaurant;

import static com.woowacourse.matzip.MemberFixtures.HUNI;
import static com.woowacourse.matzip.MemberFixtures.ORI;
import static com.woowacourse.matzip.TestFixtureCreateUtil.createTestBookmark;
import static com.woowacourse.matzip.TestFixtureCreateUtil.createTestMember;
import static com.woowacourse.matzip.TestFixtureCreateUtil.createTestRestaurant;
import static com.woowacourse.matzip.TestFixtureCreateUtil.createTestReview;
import static com.woowacourse.matzip.infrastructure.restaurant.RestaurantFindQueryFactory.ORDER_BY_BOOKMARK_COUNT_DESC;
import static com.woowacourse.matzip.infrastructure.restaurant.RestaurantFindQueryFactory.ORDER_BY_DISTANCE_ASC;
import static com.woowacourse.matzip.infrastructure.restaurant.RestaurantFindQueryFactory.ORDER_BY_ID_DESC;
import static com.woowacourse.matzip.infrastructure.restaurant.RestaurantFindQueryFactory.ORDER_BY_NAME_ASC;
import static com.woowacourse.matzip.infrastructure.restaurant.RestaurantFindQueryFactory.ORDER_BY_RATING_DESC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.bookmark.BookmarkRepository;
import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import com.woowacourse.support.config.JpaConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@Import(JpaConfig.class)
public class RestaurantQueryRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantQueryRepository restaurantQueryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Test
    void 캠퍼스id가_일치하는_식당을_최신순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "식당3", "주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        String query = ORDER_BY_ID_DESC.getQuery();
        Slice<Restaurant> page1 = restaurantQueryRepository.findPageByCampusIdAndCategoryId(query, 1L, null,
                PageRequest.of(0, 2));
        Slice<Restaurant> page2 = restaurantQueryRepository.findPageByCampusIdAndCategoryId(query, 1L, null,
                PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(page1).containsExactly(restaurant3, restaurant2),
                () -> assertThat(page2).containsExactly(restaurant1)
        );
    }

    @Test
    void 캠퍼스id가_일치하는_식당을_가나다순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "가식당", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "나식당", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "다식당", "주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        String query = ORDER_BY_NAME_ASC.getQuery();
        Slice<Restaurant> page1 = restaurantQueryRepository.findPageByCampusIdAndCategoryId(query, 1L, 1L,
                PageRequest.of(0, 2));
        Slice<Restaurant> page2 = restaurantQueryRepository.findPageByCampusIdAndCategoryId(query, 1L, 1L,
                PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(page1).containsExactly(restaurant1, restaurant2),
                () -> assertThat(page2).containsExactly(restaurant3)
        );
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당을_최신순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(2L, 1L, "식당3", "주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        String query = ORDER_BY_ID_DESC.getQuery();
        Slice<Restaurant> page1 = restaurantQueryRepository.findPageByCampusIdAndCategoryId(query, 1L, 1L,
                PageRequest.of(0, 2));

        assertThat(page1).containsExactly(restaurant2, restaurant1);
    }

    @Test
    void 캠퍼스id가_일치하는_식당을_별점순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "식당3", "주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        Member member = createTestMember();
        memberRepository.save(member);
        for (int i = 0; i < 3; i++) {
            reviewRepository.save(createTestReview(member, restaurant1.getId(), 5));
            reviewRepository.save(createTestReview(member, restaurant2.getId(), 1));
        }

        String query = ORDER_BY_RATING_DESC.getQuery();
        Slice<Restaurant> page = restaurantQueryRepository.findPageByCampusIdAndCategoryId(query, 1L, 1L,
                PageRequest.of(0, 3));
        assertThat(page.getContent()).containsExactly(restaurant1, restaurant2, restaurant3);
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당을_가까운_거리순_가나다순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1", 5);
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2", 7);
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "식당3", "주소3", 6);
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        String query = ORDER_BY_DISTANCE_ASC.getQuery();
        Slice<Restaurant> page = restaurantQueryRepository.findPageByCampusIdAndCategoryId(query, 1L, 1L,
                PageRequest.of(0, 3));

        assertThat(page).containsExactly(restaurant1, restaurant3, restaurant2);
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당을_찜수가_많은순_가나다순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "식당3", "주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        Member ori = memberRepository.save(ORI.toMember());
        Member huni = memberRepository.save(HUNI.toMember());
        bookmarkRepository.save(createTestBookmark(ori, restaurant1));
        bookmarkRepository.save(createTestBookmark(ori, restaurant2));
        bookmarkRepository.save(createTestBookmark(huni, restaurant2));
        bookmarkRepository.save(createTestBookmark(ori, restaurant3));

        String query = ORDER_BY_BOOKMARK_COUNT_DESC.getQuery();
        Slice<Restaurant> page = restaurantQueryRepository.findPageByCampusIdAndCategoryId(query,
                1L, 1L, PageRequest.of(0, 3));

        assertThat(page).containsExactly(restaurant2, restaurant1, restaurant3);
    }

    @Test
    void 페이징_테스트() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "식당3", "주소3");
        Restaurant restaurant4 = createTestRestaurant(1L, 1L, "식당4", "주소4");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3, restaurant4));

        String query = ORDER_BY_ID_DESC.getQuery();
        Slice<Restaurant> page = restaurantQueryRepository.findPageByCampusIdAndCategoryId(query, 1L, 1L,
                PageRequest.of(0, 2));

        assertThat(page).containsExactly(restaurant4, restaurant3);
    }
}

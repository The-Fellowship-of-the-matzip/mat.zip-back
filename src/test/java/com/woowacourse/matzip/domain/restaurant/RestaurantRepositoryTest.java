package com.woowacourse.matzip.domain.restaurant;

import static com.woowacourse.matzip.domain.restaurant.SortCondition.DEFAULT;
import static com.woowacourse.matzip.domain.restaurant.SortCondition.SPELL;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestMember;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestRestaurant;
import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestReview;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 캠퍼스id가_일치하는_식당을_최신순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "식당3", "주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        Slice<Restaurant> page1 = restaurantRepository.findPageByCampusId(1L, null,
                PageRequest.of(0, 2, DEFAULT.getValue()));
        Slice<Restaurant> page2 = restaurantRepository.findPageByCampusId(1L, null,
                PageRequest.of(1, 2, DEFAULT.getValue()));

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

        Slice<Restaurant> page1 = restaurantRepository.findPageByCampusId(1L, null,
                PageRequest.of(0, 2, SPELL.getValue()));
        Slice<Restaurant> page2 = restaurantRepository.findPageByCampusId(1L, null,
                PageRequest.of(1, 2, SPELL.getValue()));

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

        Slice<Restaurant> page1 = restaurantRepository.findPageByCampusId(1L, 1L,
                PageRequest.of(0, 2, DEFAULT.getValue()));

        assertThat(page1).containsExactly(restaurant2, restaurant1);
    }

    @Test
    void 캠퍼스id가_일치하는_식당을_별점순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(2L, 1L, "식당3", "주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        Member member = createTestMember();
        memberRepository.save(member);
        for (int i = 0; i < 3; i++) {
            reviewRepository.save(createTestReview(member, restaurant1.getId(), 5));
            reviewRepository.save(createTestReview(member, restaurant2.getId(), 1));
        }

        Slice<Restaurant> page = restaurantRepository.findPageByCampusIdOrderByRatingDesc(1L, null,
                PageRequest.of(0, 3));
        assertThat(page.getContent()).containsExactly(restaurant1, restaurant2, restaurant3);
    }

    @Test
    void 페이징_테스트() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "식당3", "주소3");
        Restaurant restaurant4 = createTestRestaurant(1L, 1L, "식당4", "주소4");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3, restaurant4));

        Slice<Restaurant> actual = restaurantRepository.findPageByCampusId(1L, 1L,
                PageRequest.of(0, 2, DEFAULT.getValue()));

        assertThat(actual).containsExactly(restaurant4, restaurant3);
    }

    @Test
    void 캠퍼스id에_해당하는_식당을_무작위로_지정한_개수만큼_조회한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "식당3", "주소3");
        Restaurant restaurant4 = createTestRestaurant(1L, 1L, "식당4", "주소4");
        restaurantRepository.saveAll(
                List.of(restaurant1, restaurant2, restaurant3, restaurant4));

        List<Restaurant> restaurants = restaurantRepository.findRandomsByCampusId(1L, 2);

        assertThat(restaurants).hasSize(2)
                .containsAnyOf(restaurant1, restaurant2, restaurant3, restaurant4);
    }

    @Test
    void 이름이_일치하는_레스토랑을_조회한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당sigdang 1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당SigDang 2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 2L, "식당SigDang 3", "주소3");
        Restaurant restaurant4 = createTestRestaurant(1L, 1L, "식당SigDang 4", "주소4");
        Restaurant restaurant5 = createTestRestaurant(1L, 1L, "식당sigdang 5", "주소5");
        Restaurant restaurant6 = createTestRestaurant(1L, 1L, "당식4", "주소6");
        restaurantRepository.saveAll(
                List.of(restaurant1, restaurant2, restaurant3, restaurant4, restaurant5, restaurant6));

        Slice<Restaurant> restaurants = restaurantRepository
                .findPageByCampusIdAndNameContainingIgnoreCase(1L, "식당sigdang", PageRequest.of(1, 2));

        assertThat(restaurants).hasSize(2)
                .containsAnyOf(restaurant4, restaurant5);
    }
}

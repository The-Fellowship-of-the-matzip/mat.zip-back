package com.woowacourse.matzip.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.domain.member.Member;
import com.woowacourse.matzip.domain.member.MemberRepository;
import com.woowacourse.matzip.domain.review.Review;
import com.woowacourse.matzip.domain.review.ReviewRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    void 캠퍼스id가_일치하는_식당을_내림차순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(1L, 1L, "식당3", "주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        Slice<Restaurant> page1 = restaurantRepository.findPageByCampusIdOrderByIdDesc(1L, null,
                PageRequest.of(0, 2));
        Slice<Restaurant> page2 = restaurantRepository.findPageByCampusIdOrderByIdDesc(1L, null,
                PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(page1).containsExactly(restaurant3, restaurant2),
                () -> assertThat(page2).containsExactly(restaurant1)
        );
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당을_내림차순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = createTestRestaurant(2L, 1L, "식당3", "주소3");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        Slice<Restaurant> page1 = restaurantRepository.findPageByCampusIdOrderByIdDesc(1L, 1L,
                Pageable.ofSize(2));

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
            reviewRepository.save(createTestReview(member, restaurant2.getId(), 0));
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

        Slice<Restaurant> actual = restaurantRepository.findPageByCampusIdOrderByIdDesc(1L, 1L,
                PageRequest.of(0, 2));

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

    private Restaurant createTestRestaurant(Long categoryId, Long campusId, String name, String address) {
        return Restaurant.builder()
                .categoryId(categoryId)
                .campusId(campusId)
                .name(name)
                .address(address)
                .distance(10)
                .kakaoMapUrl("테스트URL")
                .imageUrl("테스트URL")
                .build();
    }

    private Member createTestMember() {
        return Member.builder()
                .githubId("githubId")
                .username("username")
                .profileImage("url")
                .build();
    }

    private Review createTestReview(Member member, Long restaurantId, int rating) {
        return Review.builder()
                .member(member)
                .restaurantId(restaurantId)
                .content("맛있어요")
                .rating(rating)
                .menu("메뉴")
                .build();
    }
}

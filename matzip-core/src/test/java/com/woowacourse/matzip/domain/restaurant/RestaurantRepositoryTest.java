package com.woowacourse.matzip.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.matzip.TestFixtureCreateUtil;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
@EnableJpaAuditing
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 캠퍼스id에_해당하는_식당을_무작위로_지정한_개수만큼_조회한다() {
        Restaurant restaurant1 = TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당1", "주소1");
        Restaurant restaurant2 = TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당2", "주소2");
        Restaurant restaurant3 = TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당3", "주소3");
        Restaurant restaurant4 = TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당4", "주소4");
        restaurantRepository.saveAll(
                List.of(restaurant1, restaurant2, restaurant3, restaurant4));

        List<Restaurant> restaurants = restaurantRepository.findRandomsByCampusId(1L, 2);

        assertThat(restaurants).hasSize(2)
                .containsAnyOf(restaurant1, restaurant2, restaurant3, restaurant4);
    }

    @Test
    void 이름이_일치하는_레스토랑을_조회한다() {
        Restaurant restaurant1 = TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당sigdang 1", "주소1");
        Restaurant restaurant2 = TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당SigDang 2", "주소2");
        Restaurant restaurant3 = TestFixtureCreateUtil.createTestRestaurant(1L, 2L, "식당SigDang 3", "주소3");
        Restaurant restaurant4 = TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당SigDang 4", "주소4");
        Restaurant restaurant5 = TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당sigdang 5", "주소5");
        Restaurant restaurant6 = TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "당식4", "주소6");
        restaurantRepository.saveAll(
                List.of(restaurant1, restaurant2, restaurant3, restaurant4, restaurant5, restaurant6));

        Slice<Restaurant> restaurants = restaurantRepository
                .findPageByCampusIdAndNameContainingIgnoreCase(1L, "식당sigdang", PageRequest.of(1, 2));

        assertThat(restaurants).hasSize(2)
                .containsAnyOf(restaurant4, restaurant5);
    }

    @Test
    void 리뷰점수를_입력받아_레스토랑_평점을_업데이트한다() {
        Restaurant restaurant = restaurantRepository.save(
                TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당sigdang 1", "주소1"));

        restaurantRepository.updateRestaurantRatingByReviewInsert(restaurant.getId(), 3);
        restaurantRepository.updateRestaurantRatingByReviewInsert(restaurant.getId(), 5);
        Restaurant actual = restaurantRepository.findById(restaurant.getId())
                .get();

        assertAll(
                () -> assertThat(actual.getReviewCount()).isEqualTo(2),
                () -> assertThat(actual.getReviewRatingSum()).isEqualTo(8),
                () -> assertThat(actual.getReviewRatingAverage()).isEqualTo(4)
        );
    }

    @Test
    void 리뷰삭제점수를_입력받아_레스토랑_평점을_업데이트한다() {
        Restaurant restaurant = restaurantRepository.save(
                TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당sigdang 1", "주소1"));
        restaurantRepository.updateRestaurantRatingByReviewInsert(restaurant.getId(), 3);
        restaurantRepository.updateRestaurantRatingByReviewInsert(restaurant.getId(), 5);

        restaurantRepository.updateRestaurantRatingByReviewDelete(restaurant.getId(), 5);
        Restaurant actual = restaurantRepository.findById(restaurant.getId())
                .get();

        assertAll(
                () -> assertThat(actual.getReviewCount()).isEqualTo(1),
                () -> assertThat(actual.getReviewRatingSum()).isEqualTo(3),
                () -> assertThat(actual.getReviewRatingAverage()).isEqualTo(3)
        );
    }

    @Test
    void 리뷰점수를_모두_삭제한값을_업데이트한다() {
        Restaurant restaurant = restaurantRepository.save(
                TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당sigdang 1", "주소1"));
        restaurantRepository.updateRestaurantRatingByReviewInsert(restaurant.getId(), 3);
        restaurantRepository.updateRestaurantRatingByReviewInsert(restaurant.getId(), 5);

        restaurantRepository.updateRestaurantRatingByReviewDelete(restaurant.getId(), 3);
        restaurantRepository.updateRestaurantRatingByReviewDelete(restaurant.getId(), 5);
        Restaurant actual = restaurantRepository.findById(restaurant.getId())
                .get();

        assertAll(
                () -> assertThat(actual.getReviewCount()).isZero(),
                () -> assertThat(actual.getReviewRatingSum()).isZero(),
                () -> assertThat(actual.getReviewRatingAverage()).isZero()
        );
    }

    @Test
    void 기존_리뷰점수를_업데이트한다() {
        Restaurant restaurant = restaurantRepository.save(
                TestFixtureCreateUtil.createTestRestaurant(1L, 1L, "식당sigdang 1", "주소1"));
        restaurantRepository.updateRestaurantRatingByReviewInsert(restaurant.getId(), 3);
        restaurantRepository.updateRestaurantRatingByReviewInsert(restaurant.getId(), 5);

        restaurantRepository.updateRestaurantRatingByReviewUpdate(restaurant.getId(), -2);
        Restaurant actual = restaurantRepository.findById(restaurant.getId())
                .get();

        assertAll(
                () -> assertThat(actual.getReviewCount()).isEqualTo(2),
                () -> assertThat(actual.getReviewRatingSum()).isEqualTo(6),
                () -> assertThat(actual.getReviewRatingAverage()).isEqualTo(3)
        );
    }
}

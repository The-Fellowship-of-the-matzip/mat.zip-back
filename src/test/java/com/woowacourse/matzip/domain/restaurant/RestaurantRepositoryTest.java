package com.woowacourse.matzip.domain.restaurant;

import static com.woowacourse.matzip.support.TestFixtureCreateUtil.createTestRestaurant;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.support.config.JpaConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
@Import(JpaConfig.class)
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

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

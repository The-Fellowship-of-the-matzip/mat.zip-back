package com.woowacourse.matzip.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 캠퍼스id가_일치하는_식당_목록을_반환한다() {
        Restaurant restaurant = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당")
                .address("주소")
                .distance(10L)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        restaurantRepository.save(restaurant);

        List<Restaurant> actual = restaurantRepository.findByCampusId(1L);

        assertThat(actual).containsOnly(restaurant);
    }
}

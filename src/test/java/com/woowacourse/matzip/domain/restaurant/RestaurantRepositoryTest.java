package com.woowacourse.matzip.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 캠퍼스id가_일치하는_식당을_내림차순으로_페이징해서_반환한다() {
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

        Page<Restaurant> actual = restaurantRepository.findByCampusIdOrderByIdDesc(1L, Pageable.ofSize(10));

        assertThat(actual).containsOnly(restaurant);
    }
}

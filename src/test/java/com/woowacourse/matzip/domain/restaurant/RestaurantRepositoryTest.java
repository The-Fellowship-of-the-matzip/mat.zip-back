package com.woowacourse.matzip.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
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
        Restaurant restaurant1 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당1")
                .address("주소1")
                .distance(10L)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant2 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당2")
                .address("주소2")
                .distance(10L)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2));

        Page<Restaurant> actual = restaurantRepository.findByCampusIdOrderByIdDesc(1L, Pageable.ofSize(10));

        assertThat(actual).containsExactly(restaurant2, restaurant1);
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당을_내림차순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당1")
                .address("주소1")
                .distance(10L)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant2 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당2")
                .address("주소2")
                .distance(10L)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2));

        Page<Restaurant> actual = restaurantRepository.findByCampusIdAndCategoryIdOrderByIdDesc(1L, 1L,
                Pageable.ofSize(10));

        assertThat(actual).containsExactly(restaurant2, restaurant1);
    }
}

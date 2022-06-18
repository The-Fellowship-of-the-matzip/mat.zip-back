package com.woowacourse.matzip.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 캠퍼스id가_일치하는_식당을_내림차순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당1")
                .address("주소1")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant2 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당2")
                .address("주소2")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant3 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당3")
                .address("주소3")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        List<Restaurant> page1 = restaurantRepository.findPageByCampusIdOrderByIdDesc(1L, null,
                PageRequest.of(0, 2));
        List<Restaurant> page2 = restaurantRepository.findPageByCampusIdOrderByIdDesc(1L, null,
                PageRequest.of(1, 2));

        assertAll(
                () -> assertThat(page1).containsExactly(restaurant3, restaurant2),
                () -> assertThat(page2).containsExactly(restaurant1)
        );
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당을_내림차순으로_페이징해서_반환한다() {
        Restaurant restaurant1 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당1")
                .address("주소1")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant2 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당2")
                .address("주소2")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant3 = Restaurant.builder()
                .categoryId(2L)
                .campusId(1L)
                .name("식당3")
                .address("주소3")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        List<Restaurant> page1 = restaurantRepository.findPageByCampusIdOrderByIdDesc(1L, 1L,
                Pageable.ofSize(2));

        assertThat(page1).containsExactly(restaurant2, restaurant1);
    }

    @Test
    void 페이징_테스트() {
        Restaurant restaurant1 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당1")
                .address("주소1")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant2 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당2")
                .address("주소2")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant3 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당3")
                .address("주소3")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant4 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당4")
                .address("주소4")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        restaurantRepository.saveAll(
                List.of(restaurant1, restaurant2, restaurant3, restaurant4));

        List<Restaurant> actual = restaurantRepository.findPageByCampusIdOrderByIdDesc(1L, 1L,
                PageRequest.of(0, 2));

        assertThat(actual).containsExactly(restaurant4, restaurant3);
    }

    @Test
    void 캠퍼스id에_해당하는_식당을_무작위로_지정한_개수만큼_조회한다() {
        Restaurant restaurant1 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당1")
                .address("주소1")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant2 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당2")
                .address("주소2")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant3 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당3")
                .address("주소3")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        Restaurant restaurant4 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당4")
                .address("주소4")
                .distance(10)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.test.com")
                .build();
        restaurantRepository.saveAll(
                List.of(restaurant1, restaurant2, restaurant3, restaurant4));

        List<Restaurant> restaurants = restaurantRepository.findRandomsByCampusId(1L, 2);

        assertThat(restaurants).hasSize(2)
                .containsAnyOf(restaurant1, restaurant2, restaurant3, restaurant4);
    }
}

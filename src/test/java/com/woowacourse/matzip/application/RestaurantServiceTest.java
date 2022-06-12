package com.woowacourse.matzip.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.matzip.application.response.RestaurantResponse;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 캠퍼스id가_일치하는_식당_목록을_반환한다() {
        Restaurant restaurant1 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당1")
                .address("식당1주소")
                .distance(10L)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.image.test.com")
                .build();
        Restaurant restaurant2 = Restaurant.builder()
                .categoryId(1L)
                .campusId(1L)
                .name("식당2")
                .address("식당2주소")
                .distance(10L)
                .kakaoMapUrl("www.kakao.test.com")
                .imageUrl("www.image.test.com")
                .build();
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2));

        List<RestaurantResponse> responses = restaurantService.findByCampusId(1L);

        assertThat(responses).hasSize(2)
                .extracting("id")
                .containsOnly(restaurant1.getId(), restaurant2.getId());
    }
}

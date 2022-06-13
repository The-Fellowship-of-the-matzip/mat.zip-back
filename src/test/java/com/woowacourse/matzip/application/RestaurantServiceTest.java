package com.woowacourse.matzip.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.matzip.application.response.RestaurantResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void 캠퍼스id가_일치하는_식당_목록_페이지를_반환한다() {
        List<RestaurantResponse> responses = restaurantService.findByCampusIdOrderByIdDesc(2L, Pageable.ofSize(10));

        assertThat(responses).hasSize(3);
    }

    @Test
    void 캠퍼스id와_카테고리id가_일치하는_식당_목록_페이지를_반환한다() {
        List<RestaurantResponse> responses = restaurantService.findByCampusIdAndCategoryIdOrderByIdDesc(2L, 1L,
                Pageable.ofSize(10));

        assertThat(responses).hasSize(2);
    }
}

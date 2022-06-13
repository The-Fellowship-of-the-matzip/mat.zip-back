package com.woowacourse.matzip.presentation;

import com.woowacourse.matzip.application.RestaurantService;
import com.woowacourse.matzip.application.response.RestaurantResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/campuses/{campusId}/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> findByCampusId(@PathVariable final Long campusId) {
        return ResponseEntity.ok(restaurantService.findByCampusId(campusId));
    }
}

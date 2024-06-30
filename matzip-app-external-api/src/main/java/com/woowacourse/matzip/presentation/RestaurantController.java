package com.woowacourse.matzip.presentation;

import com.woowacourse.auth.support.AuthenticationPrincipal;
import com.woowacourse.matzip.application.RestaurantService;
import com.woowacourse.matzip.application.response.RestaurantResponse;
import com.woowacourse.matzip.application.response.RestaurantSearchesResponse;
import com.woowacourse.matzip.application.response.RestaurantTitleResponse;
import com.woowacourse.matzip.application.response.RestaurantTitlesResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/campuses/{campusId}/restaurants")
    public ResponseEntity<RestaurantTitlesResponse> showPage(
            @PathVariable final Long campusId,
            @AuthenticationPrincipal final String githubId,
            @RequestParam(required = false) final Long categoryId,
            @RequestParam(value = "filter", defaultValue = "DEFAULT") final String filterName,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(
                restaurantService.findByCampusIdAndCategoryId(githubId, filterName, campusId, categoryId, pageable));
    }

    @GetMapping("/campuses/{campusId}/restaurants/random")
    public ResponseEntity<List<RestaurantTitleResponse>> showRandoms(
            @PathVariable final Long campusId,
            @AuthenticationPrincipal final String githubId,
            @RequestParam final int size
    ) {
        return ResponseEntity.ok(restaurantService.findRandomsByCampusId(githubId, campusId, size));
    }

    @GetMapping("/campuses/{campusId}/restaurants/search")
    public ResponseEntity<RestaurantTitlesResponse> searchRestaurantsPage(
            @PathVariable final Long campusId,
            @AuthenticationPrincipal final String githubId,
            @RequestParam final String name,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(
                restaurantService.findTitlesByCampusIdAndNameContainingIgnoreCaseIdDescSort(
                        githubId,
                        campusId,
                        name,
                        pageable)
        );
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantResponse> showRestaurant(@PathVariable final Long restaurantId,
                                                             @AuthenticationPrincipal final String githubId) {
        return ResponseEntity.ok(restaurantService.findById(githubId, restaurantId));
    }

    @GetMapping("/restaurants/bookmarks")
    public ResponseEntity<List<RestaurantTitleResponse>> showBookmarkedRestaurants(
            @AuthenticationPrincipal final String githubId
    ) {
        return ResponseEntity.ok(restaurantService.findBookmarkedRestaurants(githubId));
    }

    @GetMapping("/campuses/{campusId}/restaurants/search/autocomplete")
    public ResponseEntity<RestaurantSearchesResponse> autocompleteSearchRestaurants(@PathVariable final Long campusId,
                                                                                        @RequestParam final String namePrefix) {
        return ResponseEntity.ok(restaurantService.findByRestaurantNamePrefix(campusId, namePrefix, Pageable.ofSize(5)));
    }
}

package com.woowacourse.matzip.presentation;

import com.woowacourse.auth.support.AuthenticationPrincipal;
import com.woowacourse.matzip.application.RestaurantDemandService;
import com.woowacourse.matzip.application.response.RestaurantDemandsResponse;
import com.woowacourse.matzip.presentation.request.RestaurantDemandCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantDemandUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/campuses/{campusId}/restaurantDemands")
public class RestaurantDemandController {

    private final RestaurantDemandService restaurantDemandService;

    public RestaurantDemandController(final RestaurantDemandService restaurantDemandService) {
        this.restaurantDemandService = restaurantDemandService;
    }

    @PostMapping
    public ResponseEntity<Void> createRestaurantRequest(@PathVariable final Long campusId,
                                                        @AuthenticationPrincipal final String githubId,
                                                        @Valid @RequestBody final RestaurantDemandCreateRequest restaurantDemandCreateRequest) {
        restaurantDemandService.createDemand(githubId, campusId, restaurantDemandCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<RestaurantDemandsResponse> showPage(@PathVariable final Long campusId,
                                                              @AuthenticationPrincipal final String githubId,
                                                              final Pageable pageable) {
        return ResponseEntity.ok(restaurantDemandService.findPage(githubId, campusId, pageable));
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<Void> updateRestaurantRequest(@PathVariable final Long campusId,
                                                        @PathVariable final Long requestId,
                                                        @AuthenticationPrincipal final String githubId,
                                                        @Valid @RequestBody final RestaurantDemandUpdateRequest restaurantDemandUpdateRequest) {
        restaurantDemandService.updateDemand(githubId, requestId, restaurantDemandUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteRestaurantRequest(@PathVariable final Long campusId,
                                                        @PathVariable final Long requestId,
                                                        @AuthenticationPrincipal final String githubId) {
        restaurantDemandService.deleteDemand(githubId, requestId);
        return ResponseEntity.noContent().build();
    }
}

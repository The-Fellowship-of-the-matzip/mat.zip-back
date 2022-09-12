package com.woowacourse.matzip.presentation;

import com.woowacourse.auth.support.AuthenticationPrincipal;
import com.woowacourse.matzip.application.RestaurantRequestService;
import com.woowacourse.matzip.application.response.RestaurantRequestsResponse;
import com.woowacourse.matzip.presentation.request.RestaurantRequestCreateRequest;
import com.woowacourse.matzip.presentation.request.RestaurantRequestUpdateRequest;
import javax.validation.Valid;
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
@RequestMapping("/api/campuses/{campusId}/restaurants/requests")
public class RestaurantRequestController {

    private final RestaurantRequestService restaurantRequestService;

    public RestaurantRequestController(final RestaurantRequestService restaurantRequestService) {
        this.restaurantRequestService = restaurantRequestService;
    }

    @PostMapping
    public ResponseEntity<Void> createRestaurantRequest(@PathVariable final Long campusId,
                                                        @AuthenticationPrincipal final String githubId,
                                                        @Valid @RequestBody final RestaurantRequestCreateRequest restaurantRequestCreateRequest) {
        restaurantRequestService.createRequest(githubId, campusId, restaurantRequestCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<RestaurantRequestsResponse> showPage(@PathVariable final Long campusId,
                                                               @AuthenticationPrincipal final String githubId,
                                                               final Pageable pageable) {
        return ResponseEntity.ok(restaurantRequestService.findPage(githubId, campusId, pageable));
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<Void> updateRestaurantRequest(@PathVariable final Long campusId,
                                                        @PathVariable final Long requestId,
                                                        @AuthenticationPrincipal final String githubId,
                                                        @Valid @RequestBody final RestaurantRequestUpdateRequest restaurantRequestUpdateRequest) {
        restaurantRequestService.updateRequest(githubId, requestId, restaurantRequestUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteRestaurantRequest(@PathVariable final Long campusId,
                                                        @PathVariable final Long requestId,
                                                        @AuthenticationPrincipal final String githubId) {
        restaurantRequestService.deleteRequest(githubId, requestId);
        return ResponseEntity.noContent().build();
    }
}

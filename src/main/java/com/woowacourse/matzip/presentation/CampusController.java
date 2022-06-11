package com.woowacourse.matzip.presentation;

import com.woowacourse.matzip.application.CampusService;
import com.woowacourse.matzip.application.response.CampusResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/campuses")
public class CampusController {

    private final CampusService campusService;

    public CampusController(final CampusService campusService) {
        this.campusService = campusService;
    }

    @GetMapping
    public ResponseEntity<List<CampusResponse>> findAll() {
        return ResponseEntity.ok(campusService.findAll());
    }
}

package com.woowacourse.auth.presentation;

import com.woowacourse.auth.application.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/api/login")
    public ResponseEntity<TokenResponse> login(@RequestParam String code) {
        return ResponseEntity.ok(new TokenResponse(code));
    }
}

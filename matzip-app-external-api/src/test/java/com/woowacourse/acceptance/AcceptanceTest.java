package com.woowacourse.acceptance;

import static com.woowacourse.matzip.config.Profile.TEST;

import com.woowacourse.support.SpringAcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(TEST)
@SpringAcceptanceTest
public class AcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DataClearManager dataClearManager;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    @AfterEach
    void afterEach() {
        dataClearManager.clear();
    }
}

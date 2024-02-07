package com.woowacourse.acceptance;

import com.woowacourse.support.DataClearManager;
import com.woowacourse.support.SpringAcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

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

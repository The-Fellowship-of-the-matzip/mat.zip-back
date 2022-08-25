package com.woowacourse.support.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiQueryCounterAopTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void Request_Scope에서_DataSource의_getConnection을_호출하면_프록시를_반환한다() throws SQLException {
        Connection actual = dataSource.getConnection();

        assertAll(
                () -> assertThat(actual).isInstanceOf(Proxy.class),
                () -> assertThat(actual).isInstanceOf(Connection.class)
        );
    }
}

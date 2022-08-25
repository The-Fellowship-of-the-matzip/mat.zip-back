package com.woowacourse.support.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConnectionProxyHandlerTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;

    @Test
    void prepareStatement를_호출하면_PreparedStatement의_다이나믹_프록시를_반환한다() throws SQLException {
        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);

        ApiQueryCounter apiQueryCounter = new ApiQueryCounter();
        ConnectionProxyHandler connectionProxyHandler = new ConnectionProxyHandler(connection, apiQueryCounter);
        Connection proxy = (Connection) Proxy.newProxyInstance(
                connection.getClass().getClassLoader(),
                connection.getClass().getInterfaces(),
                connectionProxyHandler
        );

        PreparedStatement actual = proxy.prepareStatement("");

        assertAll(
                () -> assertThat(actual).isInstanceOf(Proxy.class),
                () -> assertThat(actual).isInstanceOf(PreparedStatement.class)
        );
    }
}

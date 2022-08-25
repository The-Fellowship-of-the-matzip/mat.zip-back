package com.woowacourse.support.logging;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PreparedStatementProxyHandlerTest {

    @Mock
    private PreparedStatement preparedStatement;

    @Test
    void executeQuery를_호출하면_count를_증가시킨다() throws SQLException {
        ApiQueryCounter apiQueryCounter = new ApiQueryCounter();
        PreparedStatementProxyHandler preparedStatementProxyHandler = new PreparedStatementProxyHandler(
                preparedStatement, apiQueryCounter);
        PreparedStatement proxy = (PreparedStatement) Proxy.newProxyInstance(
                preparedStatement.getClass().getClassLoader(),
                preparedStatement.getClass().getInterfaces(),
                preparedStatementProxyHandler
        );

        proxy.executeQuery();

        assertThat(apiQueryCounter.getCount()).isOne();
    }
}

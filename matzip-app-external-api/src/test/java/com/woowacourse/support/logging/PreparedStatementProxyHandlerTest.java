package com.woowacourse.support.logging;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
public class PreparedStatementProxyHandlerTest {

    @Mock
    private PreparedStatement preparedStatement;

    @Test
    void Request_Scope에서_executeQuery를_호출하면_count를_증가시킨다() throws SQLException {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        ApiQueryCounter apiQueryCounter = new ApiQueryCounter();
        PreparedStatementProxyHandler preparedStatementProxyHandler = new PreparedStatementProxyHandler(
                preparedStatement, apiQueryCounter);
        PreparedStatement proxy = (PreparedStatement) Proxy.newProxyInstance(
                preparedStatement.getClass().getClassLoader(),
                preparedStatement.getClass().getInterfaces(),
                preparedStatementProxyHandler
        );

        proxy.executeQuery();
        RequestContextHolder.resetRequestAttributes();

        assertThat(apiQueryCounter.getCount()).isOne();
    }

    @Test
    void Request_Scope에서_execute를_호출하면_count를_증가시킨다() throws SQLException {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        ApiQueryCounter apiQueryCounter = new ApiQueryCounter();
        PreparedStatementProxyHandler preparedStatementProxyHandler = new PreparedStatementProxyHandler(
                preparedStatement, apiQueryCounter);
        PreparedStatement proxy = (PreparedStatement) Proxy.newProxyInstance(
                preparedStatement.getClass().getClassLoader(),
                preparedStatement.getClass().getInterfaces(),
                preparedStatementProxyHandler
        );

        proxy.execute();
        RequestContextHolder.resetRequestAttributes();

        assertThat(apiQueryCounter.getCount()).isOne();
    }

    @Test
    void Request_Scope에서_executeUpdate를_호출하면_count를_증가시킨다() throws SQLException {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        ApiQueryCounter apiQueryCounter = new ApiQueryCounter();
        PreparedStatementProxyHandler preparedStatementProxyHandler = new PreparedStatementProxyHandler(
                preparedStatement, apiQueryCounter);
        PreparedStatement proxy = (PreparedStatement) Proxy.newProxyInstance(
                preparedStatement.getClass().getClassLoader(),
                preparedStatement.getClass().getInterfaces(),
                preparedStatementProxyHandler
        );

        proxy.executeUpdate();
        RequestContextHolder.resetRequestAttributes();

        assertThat(apiQueryCounter.getCount()).isOne();
    }

    @Test
    void Request_Scope가_아닐_때는_count를_증가시키지_않는다() throws SQLException {
        ApiQueryCounter apiQueryCounter = new ApiQueryCounter();
        PreparedStatementProxyHandler preparedStatementProxyHandler = new PreparedStatementProxyHandler(
                preparedStatement, apiQueryCounter);
        PreparedStatement proxy = (PreparedStatement) Proxy.newProxyInstance(
                preparedStatement.getClass().getClassLoader(),
                preparedStatement.getClass().getInterfaces(),
                preparedStatementProxyHandler
        );

        proxy.executeQuery();

        assertThat(apiQueryCounter.getCount()).isZero();
    }
}

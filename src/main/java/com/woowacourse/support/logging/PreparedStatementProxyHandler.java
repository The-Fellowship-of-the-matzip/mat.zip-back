package com.woowacourse.support.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.web.context.request.RequestContextHolder;

public class PreparedStatementProxyHandler implements InvocationHandler {

    private final Object preparedStatement;
    private final ApiQueryCounter apiQueryCounter;

    public PreparedStatementProxyHandler(final Object preparedStatement, final ApiQueryCounter apiQueryCounter) {
        this.preparedStatement = preparedStatement;
        this.apiQueryCounter = apiQueryCounter;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (method.getName().equals("executeQuery") && isInRequestScope()) {
            apiQueryCounter.increaseCount();
        }
        return method.invoke(preparedStatement, args);
    }

    private boolean isInRequestScope() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}

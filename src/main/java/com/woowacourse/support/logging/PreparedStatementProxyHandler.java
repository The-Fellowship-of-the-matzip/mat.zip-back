package com.woowacourse.support.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PreparedStatementProxyHandler implements InvocationHandler {

    private final Object preparedStatement;
    private final ApiQueryCounter apiQueryCounter;

    public PreparedStatementProxyHandler(final Object preparedStatement, final ApiQueryCounter apiQueryCounter) {
        this.preparedStatement = preparedStatement;
        this.apiQueryCounter = apiQueryCounter;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (method.getName().equals("executeQuery")) {
            apiQueryCounter.increaseCount();
        }
        return method.invoke(preparedStatement, args);
    }
}

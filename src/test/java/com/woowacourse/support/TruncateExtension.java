package com.woowacourse.support;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class TruncateExtension implements InvocationInterceptor {

    @Override
    public <T> T interceptTestClassConstructor(final Invocation<T> invocation,
                                               final ReflectiveInvocationContext<Constructor<T>> invocationContext,
                                               final ExtensionContext extensionContext) throws Throwable {
        final DataClearManager dataClearManager = getDataClearManager(extensionContext);
        dataClearManager.clear();
        return invocation.proceed();
    }

    private DataClearManager getDataClearManager(final ExtensionContext extensionContext) {
        return  (DataClearManager) SpringExtension.getApplicationContext(
                extensionContext).getBean("dataClearManager");
    }
}

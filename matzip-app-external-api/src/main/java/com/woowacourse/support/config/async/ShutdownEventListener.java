package com.woowacourse.support.config.async;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ShutdownEventListener implements ApplicationListener<ContextClosedEvent> {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public ShutdownEventListener(final ApplicationContext applicationContext) {
        this.threadPoolTaskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("asyncTaskExecutor");
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        final int activeCount = threadPoolTaskExecutor.getActiveCount();

        if (activeCount > 0) {
            threadPoolTaskExecutor.setAwaitTerminationMillis(15000);
        }
    }
}

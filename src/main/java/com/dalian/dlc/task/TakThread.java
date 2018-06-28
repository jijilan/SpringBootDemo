package com.dalian.dlc.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import static java.util.concurrent.Executors.*;

/**
 * 配置多线程并发执行定时任务
 */
@Configuration
public class TakThread implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(newScheduledThreadPool(2));
    }
}

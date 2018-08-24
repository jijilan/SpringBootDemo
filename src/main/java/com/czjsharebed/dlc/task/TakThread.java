package com.czjsharebed.dlc.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import static java.util.concurrent.Executors.*;

/**
 * @Author: liujiebang
 * @Description:
 *                配置多线程并发执行定时任务,根据项目实际情况而定.
 * @Date: 2018/7/2 16:55
 **/
@Configuration
public class TakThread implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(newScheduledThreadPool(2));
    }
}

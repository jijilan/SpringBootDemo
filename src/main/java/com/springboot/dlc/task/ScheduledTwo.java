package com.springboot.dlc.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: liujiebang
 * @Description: 定时器two
 * @Date: 2018/7/2 16:55
 **/
@Slf4j
@Component
public class ScheduledTwo {


    @Scheduled(cron = "0 0/5 * * * ?")
    public void executeUploadTask() {
        // 间隔5分钟,执行工单上传任务
        Thread current = Thread.currentThread();
        log.debug("定时任务2:{}", current.getId());
        log.debug("ScheduledTest.executeUploadTask 定时任务2:" + current.getId() + ",name:" + current.getName());
    }
}

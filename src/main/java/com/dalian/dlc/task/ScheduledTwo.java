package com.dalian.dlc.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 */
@Slf4j
@Component
public class ScheduledTwo {


    @Scheduled(cron = "0 0/5 * * * ?")
    public void executeUploadTask() {
        // 间隔5分钟,执行工单上传任务
        Thread current = Thread.currentThread();
        log.info("定时任务2:{}", current.getId());
        log.info("ScheduledTest.executeUploadTask 定时任务2:" + current.getId() + ",name:" + current.getName());
    }
}

package com.czjsharebed.dlc.task;



import lombok.extern.slf4j.Slf4j;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @Author: liujiebang
 * @Description: 定时器one
 * @Date: 2018/7/2 16:55
 **/
@Slf4j
@Component
public class ScheduledOne {


    @Scheduled(cron = "0 0/1 * * * ?")
    public void executeUploadTask() {

        // 间隔5分钟,执行工单上传任务
        Thread current = Thread.currentThread();
        log.info("定时任务1:{}",current.getId());
        log.info("ScheduledTest.executeUploadTask 定时任务1:"+current.getId() + ",name:"+current.getName());
    }


}

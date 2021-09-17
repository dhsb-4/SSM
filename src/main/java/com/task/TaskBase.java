package com.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 */
@Component
public class TaskBase {

    @Scheduled(cron = "*/5 * * * * *")
    public void show(){
        System.out.println("每秒执行一次定时任务");
    }
}

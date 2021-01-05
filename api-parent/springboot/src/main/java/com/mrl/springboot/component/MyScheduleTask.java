package com.mrl.springboot.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyScheduleTask
 * @Description
 * @Author Mr.L
 * @Date 2020/12/4 15:49
 * @Version 1.0
 */
@Slf4j
@Component
public class MyScheduleTask {

    @Scheduled(cron = "0-3 * * * * *")
    public void cancel() {
        log.info("exec...");
    }
}

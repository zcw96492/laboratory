package com.newcore.laboratory.batch.service.listener;

import com.newcore.laboratory.api.ScheduleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 定时任务监听器
 * @author zhouchaowei
 */
@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobInitListener.class);

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Override
    public void run(String... arg0){
        try {
            scheduleTaskService.initSchedule();
        } catch (Exception e) {
            logger.error("定时任务初始化失败!",e);
        }
    }
}
package com.newcore.laboratory.batch.service.api;

import com.newcore.laboratory.api.ScheduleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTaskServiceImpl.class);

    /**
     * 初始化定时器方法
     */
    @Override
    public void initSchedule() {

    }
}
